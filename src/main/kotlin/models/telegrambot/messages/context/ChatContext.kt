package models.telegrambot.messages.context

import models.telegrambot.messages.response.Message

class ChatContext : Context() {
    @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
    var message: Message?
        get() = get("message") as? Message
        set(value) = put("message", value)

    var schemaFillers: Array<Pair<String, dynamic>> = emptyArray()
}