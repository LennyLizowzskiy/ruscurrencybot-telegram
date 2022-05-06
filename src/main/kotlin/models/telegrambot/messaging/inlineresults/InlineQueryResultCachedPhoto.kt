package models.telegrambot.messaging.inlineresults

import kotlinx.serialization.Serializable
import models.telegrambot.messaging.MessageEntity
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultCachedPhoto(
    val type: String = "photo",
    override var id: String? = null,
    val photo_file_id: String,
    val title: String? = null,
    val description: String? = null,
    val caption: String? = null,
    val parse_mode: String? = null,
    val caption_entities: Array<MessageEntity>? = null,
    val reply_markup: InlineKeyboardMarkup? = null,
    val input_message_content: InputMessageContent? = null
) : InlineQueryResultItem