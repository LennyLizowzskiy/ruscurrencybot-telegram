package models.telegrambot.messaging.inlineresults

import ResourceStore
import kotlinx.serialization.Serializable
import models.telegrambot.messaging.MessageEntity
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultCachedAudio(
    val type: String = "audio",
    override var id: String? = null,
    val audio_file_id: String,
    val caption: String,
    val parse_mode: String = ResourceStore.telegramBotDefaults["parse_mode"] as String,
    val caption_entities: Array<MessageEntity>? = null,
    val reply_markup: InlineKeyboardMarkup? = null,
    val input_message_content: InputMessageContent? = null
) : InlineQueryResultItem