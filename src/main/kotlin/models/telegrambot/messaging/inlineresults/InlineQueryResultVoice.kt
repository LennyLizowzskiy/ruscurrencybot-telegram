package models.telegrambot.messaging.inlineresults

import ResourceStore
import kotlinx.serialization.Serializable
import models.telegrambot.messaging.MessageEntity
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputMessageContent

@Serializable
data class InlineQueryResultVoice(
    val type: String = "voice",
    override var id: String? = null,
    val voice_url: String,
    val title: String,
    val caption: String? = null,
    val parse_mode: String = ResourceStore.telegramBotDefaults["parse_mode"] as String,
    val voice_duration: Int? = null,
    val caption_entities: Array<MessageEntity>? = null,
    val input_message_content: InputMessageContent? = null,
    val reply_markup: InlineKeyboardMarkup? = null
) : InlineQueryResultItem