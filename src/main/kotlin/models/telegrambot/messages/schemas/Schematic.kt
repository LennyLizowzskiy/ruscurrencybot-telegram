package models.telegrambot.messages.schemas

sealed interface Schematic {
    fun applyParameters(vararg parameters: dynamic): String
}