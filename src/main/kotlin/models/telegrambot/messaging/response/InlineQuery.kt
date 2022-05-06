package models.telegrambot.messaging.response

external interface InlineQuery {
    val id: Long
    val from: Sender
    val chat_type: String
    val query: String
    val offset: String
}