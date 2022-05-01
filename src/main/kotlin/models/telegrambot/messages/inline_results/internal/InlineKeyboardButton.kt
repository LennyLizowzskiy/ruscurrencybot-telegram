package models.telegrambot.messages.inline_results.internal

data class InlineKeyboardButton(
    val text: String,
    val url: String?,
    val callback_data: String?,
    val web_app: dynamic,
    val login_url: dynamic,
    val switch_inline_query: String?,
    val switch_inline_query_current_chat: String?,
    val callback_game: dynamic,
    val pay: Boolean?
)
