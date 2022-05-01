package models.telegrambot.messages.inline_results

data class InlineQueryResultCachedDocument(
    override val type: String,
    override val id: String
) : InlineQueryResultItem(type, id)