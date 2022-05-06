package models.telegrambot.messaging.inlineresults.inlinekeyboard

import kotlinx.serialization.Serializable

@Serializable
data class InlineKeyboardMarkup(
    val inline_keyboard: Array<Array<InlineKeyboardButton>>
)