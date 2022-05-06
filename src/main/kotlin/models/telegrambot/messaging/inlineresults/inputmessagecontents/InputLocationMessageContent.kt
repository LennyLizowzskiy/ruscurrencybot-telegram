package models.telegrambot.messaging.inlineresults.inputmessagecontents

import kotlinx.serialization.Serializable

@Serializable
data class InputLocationMessageContent(
    val latitude: Float,
    val longitude: Float,
    val horizontal_accuracy: Float? = null,
    val live_period: Int? = null,
    val heading: Int? = null,
    val proximity_alert_radius: Int? = null
) : InputMessageContent()