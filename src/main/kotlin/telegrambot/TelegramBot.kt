package telegrambot

import ResourceStore
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.encodeToDynamic
import models.telegrambot.messaging.commands.ChatCommand
import models.telegrambot.messaging.commands.ChatCommandReply
import models.telegrambot.messaging.commands.Command
import models.telegrambot.messaging.commands.InlineAnswer
import models.telegrambot.messaging.commands.InlineRequest
import models.telegrambot.messaging.response.InlineQuery
import kotlin.js.RegExp
import kotlin.js.json
import javascript.dependencies.TelegramBot as TelegramBotClass
import kotlinx.serialization.json.Json as JsonS

lateinit var TelegramBot: TelegramBotClass
fun setupTelegramBot() {
    TelegramBot = TelegramBotClass(
        ResourceStore.sensitiveInformation["authorization"].asDynamic()["telegram_bot_api_key"]!! as String,
        json(
            "polling" to true
        )
    )

    //TelegramBot.on("polling_error") { err, _ -> console.log(err) }
}

fun listenChatCommands() {
    TelegramBot.onText(RegExp("(?<=\\/)\\w+")) { message, matched ->
        val command = ChatCommand.storage[matched[0]] ?: return@onText

        command.executables[Command.Events.BEFORE_REPLY]!!()
        command.reply = command.executables[Command.Events.ANSWER]!!() as ChatCommandReply

        command.context.message = message

        val options = json(
            "parse_mode" to ResourceStore.telegramBotDefaults["parse_mode"] as String,
            "disable_web_page_preview" to true,
            "disable_notification" to true,
            "allow_sending_without_reply" to command.reply.options.allowSendingWithoutReply
        )
        if (command.reply.options.isTelegramReply) options["reply_to_message_id"] = message.message_id

        TelegramBot.sendMessage(message.chat.id, command.reply.text, options)
        command.executables[Command.Events.AFTER_REPLY]!!()
    }
}


@OptIn(ExperimentalSerializationApi::class)
fun listenInlineQueries() {
    TelegramBot.on("inline_query") start@{ query, _ ->
        var answer: InlineRequest? = null
        var matched: MatchResult? = null
        for (entry in InlineRequest.storage) {
            if(matched != null) break

            entry.value.regexes.find { it.matches(query.query.toString()) }?.let {
                matched = it.find(query.query.toString())
                answer = entry.value
            }
        }
        if (answer == null) answer = InlineRequest.storage.getValue("default")

        answer!!.executables[Command.Events.BEFORE_REPLY]!!()

        val finalAnswer = answer!!.executables[Command.Events.ANSWER]!!() as InlineAnswer // потому что смарт-кастить оно не хочет

        finalAnswer.context.query = query.query.unsafeCast<InlineQuery>()
        finalAnswer.context.matched = matched

        val resultArray: ArrayList<dynamic> = arrayListOf()
        val jsoner = JsonS {
            classDiscriminator = "_type"
            encodeDefaults = true
            explicitNulls = false
        }
        finalAnswer.results.forEach { resultArray.add(jsoner.encodeToDynamic(it)) }

        TelegramBot.asDynamic().answerInlineQuery(query.id, resultArray.toTypedArray())
        answer!!.executables[Command.Events.AFTER_REPLY]!!()
    }
}