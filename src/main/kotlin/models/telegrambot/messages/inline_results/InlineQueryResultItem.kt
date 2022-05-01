package models.telegrambot.messages.inline_results

sealed class InlineQueryResultItem(
    open val type: String,
    open val id: String
)