package models.telegrambot.messages

/**
 * https://core.telegram.org/bots/api#messageentity
 */
data class MessageEntity(
    val type: String,
    val offset: Int,
    val length: Int,
    val url: String?,
    val user: dynamic,
    val language: String?
)