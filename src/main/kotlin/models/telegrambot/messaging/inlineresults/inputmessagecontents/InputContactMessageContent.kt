package models.telegrambot.messaging.inlineresults.inputmessagecontents

import kotlinx.serialization.Serializable

@Serializable
data class InputContactMessageContent(
    val phone_number: String,
    val first_name: String,
    val last_name: String? = null,
    val vcard: String? = null
) : InputMessageContent()