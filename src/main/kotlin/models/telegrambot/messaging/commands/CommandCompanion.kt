package models.telegrambot.messaging.commands

interface CommandCompanion<T> {
    val storage: MutableMap<String, T>
}