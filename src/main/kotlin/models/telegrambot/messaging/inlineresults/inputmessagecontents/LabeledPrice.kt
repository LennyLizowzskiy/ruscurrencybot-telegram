package models.telegrambot.messaging.inlineresults.inputmessagecontents

import kotlinx.serialization.Serializable

@Serializable
data class LabeledPrice(
    val label: String,
    val amount: Int
)
