package models.telegrambot.messages.inline_results

import models.telegrambot.messages.inline_results.internal.InlineKeyboardMarkup
import models.telegrambot.messages.inline_results.internal.InputTextMessageContent

data class InlineQueryResultArticle(
    override val type: String,
    override val id: String,
    val title: String,
    val input_message_content: InputTextMessageContent,
    val reply_markup: InlineKeyboardMarkup?,
    val url: String?,
    val hide_url: Boolean?,
    val description: String?,
    val thumb_url: String?,
    val thumb_width: Int?,
    val thumb_height: Int?
) : InlineQueryResultItem(type, id)