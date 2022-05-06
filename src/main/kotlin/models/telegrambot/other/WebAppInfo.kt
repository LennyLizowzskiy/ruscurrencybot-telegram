package models.telegrambot.other

import kotlinx.serialization.Serializable

@Serializable
data class WebAppInfo(
    val url: String
)