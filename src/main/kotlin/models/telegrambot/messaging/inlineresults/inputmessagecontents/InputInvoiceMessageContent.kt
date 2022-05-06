package models.telegrambot.messaging.inlineresults.inputmessagecontents

import kotlinx.serialization.Serializable

@Serializable
data class InputInvoiceMessageContent(
    val title: String,
    val description: String,
    val payload: String,
    val provider_token: String,
    val currency: String,
    val prices: Array<LabeledPrice>,
    val max_tip_amount: Int? = null,
    val suggested_tip_amounts: Array<Int>,
    val provider_data: String? = null,
    val photo_url: String? = null,
    val photo_size: Int? = null,
    val photo_width: Int? = null,
    val photo_height: Int? = null,
    val need_name: Boolean = false,
    val need_phone_number: Boolean = false,
    val need_email: Boolean = false,
    val need_shipping_address: Boolean = false,
    val send_phone_number_to_provider: Boolean = false,
    val is_flexible: Boolean = false
) : InputMessageContent()