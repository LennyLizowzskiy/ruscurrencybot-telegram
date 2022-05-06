package models.telegrambot.messaging.response

external interface Message {
    val message_id: Int
    val from: Sender
    val chat: MessageSenderChat
    val date: Long
    val text: String
    val entities: Array<dynamic>
}

external interface MessageSenderChat {
    val id: Long
    val title: String?
    val first_name: String?
    val last_name: String?
    val username: String?
    val type: String
}