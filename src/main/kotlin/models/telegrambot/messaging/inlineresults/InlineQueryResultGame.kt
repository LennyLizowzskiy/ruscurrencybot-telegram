package models.telegrambot.messaging.inlineresults

import kotlinx.serialization.Serializable
import models.telegrambot.messaging.inlineresults.inlinekeyboard.InlineKeyboardMarkup

@Serializable
data class InlineQueryResultGame(
    val type: String = "game",
    override var id: String? = null,
    val game_short_name: String,
    val reply_markup: InlineKeyboardMarkup? = null
) : InlineQueryResultItem