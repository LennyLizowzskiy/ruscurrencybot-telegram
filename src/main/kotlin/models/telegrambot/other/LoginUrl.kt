package models.telegrambot.other

import kotlinx.serialization.Serializable

@Serializable
data class LoginUrl(
    val url: String,
    val forward_text: String? = null,
    val bot_username: String? = null,
    val request_write_access: Boolean = false
)
