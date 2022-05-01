package models.telegrambot.messages.inline_results.internal

import models.telegrambot.messages.MessageEntity

data class InputTextMessageContent(
    val message_text: String,
    val parse_mode: String = "HTML",
    val entities: Array<MessageEntity>,
    val disable_web_page_preview: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as InputTextMessageContent

        if (message_text != other.message_text) return false
        if (parse_mode != other.parse_mode) return false
        if (!entities.contentEquals(other.entities)) return false
        if (disable_web_page_preview != other.disable_web_page_preview) return false

        return true
    }

    override fun hashCode(): Int {
        var result = message_text.hashCode()
        result = 31 * result + parse_mode.hashCode()
        result = 31 * result + entities.contentHashCode()
        result = 31 * result + disable_web_page_preview.hashCode()
        return result
    }
}