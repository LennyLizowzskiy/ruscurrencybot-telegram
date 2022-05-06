package models.telegrambot.messaging.inlineresults

import kotlinx.serialization.Serializable

@Serializable
sealed interface InlineQueryResultItem {
    var id: String?
}