package javascript.dependencies

import models.telegrambot.messaging.response.Message
import kotlin.js.Json
import kotlin.js.RegExp
import kotlin.js.RegExpMatch

/**
 * Обёртка для [Telegram Bot API](https://core.telegram.org/bots/api).
 *
 * [Открыть на NPMJS](https://www.npmjs.com/package/node-telegram-bot-api)
 */
@JsModule("node-telegram-bot-api")
@JsNonModule
external class TelegramBot(botApiKey: String, options: Json?) {
    /**
     * Активирует функцию при совершении указанного события
     *
     * [Доступные eventName](https://github.com/yagop/node-telegram-bot-api/blob/master/doc/usage.md#events)
     */
    fun on(eventName: String, callback: (dynamic, dynamic) -> Unit)

    /**
     * Совершает указанное действие при удовлетворении указанного регекса
     */
    fun onText(regexp: RegExp, callback: (Message, RegExpMatch) -> Unit)

    /**
     * Отправляет текстовое сообщение
     *
     * [Доступные поля в options](https://core.telegram.org/bots/api#sendmessage)
     */
    fun sendMessage(chat_id: dynamic, text: String, options: Json?)

    /**
     * Отвечает на [models.telegrambot.messaging.commands.InlineRequest]
     *
     * [Доступные поля в options](https://core.telegram.org/bots/api#answerinlinequery)
     */
    fun answerInlineQuery(inline_query_id: dynamic, results: Array<dynamic>)

    /**
     * @see answerInlineQuery
     */
    fun answerInlineQuery(inline_query_id: dynamic, results: Array<dynamic>, options: Json)
}