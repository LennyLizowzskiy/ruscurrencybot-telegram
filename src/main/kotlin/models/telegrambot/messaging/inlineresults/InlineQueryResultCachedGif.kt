package models.telegrambot.messaging.inlineresults

import ResourceStore
import kotlinx.serialization.Serializable
import models.telegrambot.messaging.MessageEntity
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultCachedGif(
    val type: String = "gif",
    override var id: String? = null,
    val gif_file_id: String,
    val title: String? = null,
    val caption: String? = null,
    val parse_mode: String = ResourceStore.telegramBotDefaults["parse_mode"] as String,
    val caption_entities: Array<MessageEntity>? = null,
    val reply_markup: InlineKeyboardMarkup? = null,
    val input_message_content: InputMessageContent? = null
) : InlineQueryResultItem