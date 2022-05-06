package models.telegrambot.messaging.schemas

sealed interface Schematic {
    fun applyParameters(vararg parameters: dynamic): String
}