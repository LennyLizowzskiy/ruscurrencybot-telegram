package models.telegrambot.messaging

import kotlinx.serialization.Serializable
import models.telegrambot.other.User

/**
 * https://core.telegram.org/bots/api#messageentity
 */
@Serializable
data class MessageEntity(
    val type: String,
    val offset: Int,
    val length: Int,
    val url: String?,
    val user: User,
    val language: String?
)