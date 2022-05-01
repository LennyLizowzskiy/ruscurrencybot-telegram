package models.telegrambot.messages.commands

interface CommandCompanion<T> {
    val storage: MutableMap<String, T>
}