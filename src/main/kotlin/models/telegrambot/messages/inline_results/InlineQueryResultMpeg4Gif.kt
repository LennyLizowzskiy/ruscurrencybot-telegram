package models.telegrambot.messages.inline_results

data class InlineQueryResultMpeg4Gif(
    override val type: String,
    override val id: String
) : InlineQueryResultItem(type, id)