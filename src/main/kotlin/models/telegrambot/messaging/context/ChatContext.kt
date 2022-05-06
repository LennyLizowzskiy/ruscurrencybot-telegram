package models.telegrambot.messaging.context

import models.telegrambot.messaging.response.Message

class ChatContext : Context() {
    var message: Message?
        get() = get("message")?.unsafeCast<Message>()
        set(value) = put("message", value)

    var schemaFillers: Array<Pair<String, dynamic>> = emptyArray()
}