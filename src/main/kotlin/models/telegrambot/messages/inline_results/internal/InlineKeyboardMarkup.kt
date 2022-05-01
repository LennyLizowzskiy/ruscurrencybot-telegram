package models.telegrambot.messages.inline_results.internal

data class InlineKeyboardMarkup(
    val inline_keyboard: Array<Array<InlineKeyboardButton>>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as InlineKeyboardMarkup

        if (!inline_keyboard.contentDeepEquals(other.inline_keyboard)) return false

        return true
    }

    override fun hashCode(): Int {
        return inline_keyboard.contentDeepHashCode()
    }
}
