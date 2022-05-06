package models.telegrambot.messaging.inlineresults

import ResourceStore
import kotlinx.serialization.Serializable
import models.telegrambot.messaging.MessageEntity
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultPhoto(
    val type: String = "photo",
    override var id: String? = null,
    val photo_url: String,
    val thumb_url: String,
    val photo_width: Int? = null,
    val photo_height: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val caption: String? = null,
    val parse_mode: String = ResourceStore.telegramBotDefaults["parse_mode"] as String,
    val caption_entities: Array<MessageEntity>? = null,
    val input_message_content: InputMessageContent? = null,
    val reply_markup: InlineKeyboardMarkup? = null
) : InlineQueryResultItem