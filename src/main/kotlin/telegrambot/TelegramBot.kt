@file:Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")

package telegrambot

import ResourceStore
import TelegramBot
import models.telegrambot.messages.commands.ChatCommand
import models.telegrambot.messages.commands.InlineRequest
import models.telegrambot.messages.response.InlineQuery
import kotlin.js.Json
import kotlin.js.RegExp
import kotlin.js.json

lateinit var TelegramBot: TelegramBot
fun setupTelegramBot() {
    TelegramBot = TelegramBot(
        ResourceStore.sensitiveInformation["authorization"].asDynamic()["telegram_bot_api_key"]!! as String,
        json(
            "polling" to true
        )
    )

    TelegramBot.on("polling_error") { err, _ -> console.log(err) }
}

fun listenChatCommands() {
    TelegramBot.onText(RegExp("(?<=\\/)\\w+")) { message, matched ->
        val command = ChatCommand.storage[matched[0]] ?: return@onText

        command.context.message = message

        val options = json(
            "parse_mode" to "HTML",
            "disable_web_page_preview" to true,
            "disable_notification" to true,
            "allow_sending_without_reply" to command.reply.options.allowSendingWithoutReply
        )
        if (command.reply.options.isTelegramReply) options["reply_to_message_id"] = message.message_id

        TelegramBot.sendMessage(message.chat.id, command.reply.text, options)
    }
}

fun listenInlineQueries() {
    TelegramBot.on("inline_query") start@{ query, _ ->
        var answer: InlineRequest? = null
        var matched: MatchResult? = null
        InlineRequest.storage.forEach {
            if(matched != null) return@forEach // break для бедных лол

            val regex = it.value.regexes.find { it.matches(query.query.toString()) }
            if (regex != null) {
                matched = regex.find(query.query.toString())
                answer = it.value
            }
        }
        answer!!.let {
            it.context.query = query.query as InlineQuery
            it.context.matched = matched!!

            var resultArray: Array<Json> = emptyArray()
            it.answer.results.forEach { result ->
                resultArray += json(
                    "type" to result.type,
                    "id" to result.id
                ).apply {
                    result.additions.forEach { addition ->
                        if (addition.value is Map<*, *>) {
                            json().let { json ->
                                (addition.value as Map<*, *>).forEach {additionOfAddition ->
                                    json.set(additionOfAddition.key as String, additionOfAddition.value.asDynamic())
                                }
                                set(addition.key, json)
                            }
                        } else set(addition.key, addition.value)
                    }
                }
            }

            TelegramBot.asDynamic().answerInlineQuery(query.id, resultArray)
        }
    }
}