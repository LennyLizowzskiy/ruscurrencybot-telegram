package models.telegrambot.messaging.inlineresults

import kotlinx.serialization.Serializable
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultCachedSticker(
    val type: String = "sticker",
    override var id: String? = null,
    val sticker_file_id: String,
    val reply_markup: InlineKeyboardMarkup? = null,
    val input_message_content: InputMessageContent? = null
) : InlineQueryResultItem