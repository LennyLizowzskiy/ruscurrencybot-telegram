package models.telegrambot.messaging.commands

import javascript.Timestamper
import models.telegrambot.messaging.context.ChatContext
import models.telegrambot.messaging.schemas.MessageSchema

class ChatCommandReply(override val context: ChatContext) : CommandReply(context) {
    var text: String = ""

    /**
     * Применяет указанную схему с параметрами, указанными в context.schemaFillers
     */
    fun applyMessageSchema(schema: MessageSchema) {
        text += schema.applyParameters(*context.schemaFillers)
    }

    /**
     * Добавляет метку времени в итоговое сообщение
     *
     * Используйте паттерн %t, чтобы добавить метку времени в строку
     */
    fun addTimestamp(string: String, time: Double) {
        text += "\n\n" + string.replace("%t", Timestamper.getPrettyPrintedTime(time))
    }

    fun addCurrentTimestamp() { text += "\n\n" + Timestamper.getPrettyPrintedCurrentTime() }


    var options = Options()

    /**
     * Доступные настройки:
     *
     * isTelegramReply - нужно ли "отвечать" на сообщение отправителя
     *
     * allowSendingWithoutReply - надо ли отсылать ответ, если сообщение отправителя недоступно
     */
    fun setOptions(block: Options.() -> Unit) {
        options = Options().apply(block)
    }

    class Options() {
        var isTelegramReply: Boolean = true
        var allowSendingWithoutReply: Boolean = true
    }
}