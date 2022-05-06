import models.telegrambot.messaging.response.Message
import kotlin.js.Json
import kotlin.js.RegExp
import kotlin.js.RegExpMatch
import kotlin.js.json

// TODO: Переписать аннотации и dynamic на интерфейсы

/**
 * Импорт модулей из JavaScript
 *
 * @param[moduleOrPath] Путь к файлу или название NPM-пакета
 * @return Запрошенное [Any] или пишет ошибку в терминал
 */
external fun require(moduleOrPath: String): dynamic

/**
 * Объект [process](https://nodejs.org/dist/latest-v8.x/docs/api/process.html) из Node.JS
 */
external val process: dynamic

/**
 * HTTP-клиент
 *
 * [Открыть на NPMJS](https://www.npmjs.com/package/needle)
 */
val needle = require("needle")

/**
 * Конвертер из XML в JavaScript object/JSON и наоборот
 *
 * [Открыть на NPMJS](https://www.npmjs.com/package/xml-js)
 */
val xmlConverter = require("xml-js")

/**
 * Библиотека для парсинга, валидации и форматирования времени
 *
 * [Открыть на NPMJS](https://www.npmjs.com/package/moment)
 */
val moment = require("moment")

/**
 * Chromium в формате Node
 *
 * [Открыть на NPMJS](https://www.npmjs.com/package/puppeteer)
 */
val puppeteer = require("puppeteer")

/**
 * Инициализированный объект Chromium-а
 *
 * @see puppeteer
 */
val pseudoBrowser = puppeteer.launch(json(
    /**
     * Позволит запустить бота от имени root в окружении Linux,
     * но также является большой дырой в безопасности
     *
     * Использовать на свой страх и риск
     */
    // "args" to arrayOf("--no-sandbox")
))

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
    fun answerInlineQuery(inline_query_id: dynamic, results: Array<dynamic>, options: Json?)
}

external fun setInterval(callback: () -> Unit, ms: Int)

external fun setTimeout(callback: () -> Unit, ms: Int)

fun applyJsDependenciesSettings() {
    needle.defaults(json(
        "parse_response" to false,
        "user_agent" to ResourceStore.network["user_agent"]!!
    ))
}