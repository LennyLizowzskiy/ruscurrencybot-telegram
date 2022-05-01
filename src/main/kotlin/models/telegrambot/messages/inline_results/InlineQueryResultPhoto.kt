package models.telegrambot.messages.inline_results

data class InlineQueryResultPhoto(
    override val type: String,
    override val id: String
) : InlineQueryResultItem(type, id)