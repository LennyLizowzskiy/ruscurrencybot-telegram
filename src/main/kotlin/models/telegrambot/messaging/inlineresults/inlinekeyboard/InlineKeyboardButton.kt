package models.telegrambot.messaging.inlineresults.inlinekeyboard

import kotlinx.serialization.Serializable
import models.telegrambot.other.LoginUrl
import models.telegrambot.other.WebAppInfo

@Serializable
data class InlineKeyboardButton(
    val text: String,
    val url: String?,
    val callback_data: String?,
    val web_app: WebAppInfo,
    val login_url: LoginUrl,
    val switch_inline_query: String?,
    val switch_inline_query_current_chat: String?,
    val callback_game: Int? = null,
    val pay: Boolean?
)
