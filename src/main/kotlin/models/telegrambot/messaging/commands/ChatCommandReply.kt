package models.telegrambot.messaging.commands

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