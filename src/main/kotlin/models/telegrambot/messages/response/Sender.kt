package models.telegrambot.messages.response

external interface Sender {
    val id: Long
    val is_bot: Boolean
    val first_name: String
    val last_name: String?
    val username: String?
    val language_code: String
}