package models.telegrambot.messaging.inlineresults

import ResourceStore
import kotlinx.serialization.Serializable
import models.telegrambot.messaging.MessageEntity
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultDocument(
    val type: String = "document",
    override var id: String? = null,
    val title: String,
    val caption: String? = null,
    val parse_mode: String = ResourceStore.telegramBotDefaults["parse_mode"] as String,
    val document_url: String,
    val mime_type: String,
    val description: String? = null,
    val caption_entities: Array<MessageEntity>? = null,
    val input_message_content: InputMessageContent? = null,
    val reply_markup: InlineKeyboardMarkup? = null,
    val thumb_url: String? = null,
    val thumb_width: Int? = null,
    val thumb_height: Int? = null
) : InlineQueryResultItem