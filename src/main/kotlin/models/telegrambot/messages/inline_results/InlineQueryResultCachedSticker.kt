package models.telegrambot.messages.inline_results

data class InlineQueryResultCachedSticker(
    override val type: String,
    override val id: String
) : InlineQueryResultItem(type, id)