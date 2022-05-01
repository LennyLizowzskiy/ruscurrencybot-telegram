package models.telegrambot.messages.inline_results

data class InlineQueryResultCachedGif(
    override val type: String,
    override val id: String
) : InlineQueryResultItem(type, id)
