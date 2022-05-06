package models.telegrambot.messaging.commands

import models.telegrambot.messaging.context.Context

sealed class Command {
    abstract val context: Context

    // Да, оно пиздец как бойлерплейтно реализовано, но придумать как этого нормально избежать не смог
    val executables = mutableMapOf(
        Events.BEFORE_REPLY to {},
        Events.AFTER_REPLY to {}
    )

    // Попытка придумать как избежать бойлерплейта. В зачаточном состоянии.
//    fun respond() {
//        executables[Events.BEFORE_REPLY]!!()
//
//        executables[Events.AFTER_REPLY]!!()
//    }

    fun executeBeforeReply(block: () -> Unit) {
        executables[Events.BEFORE_REPLY] = block
    }

    fun executeAfterReply(block: () -> Unit) {
        executables[Events.AFTER_REPLY] = block
    }

    enum class Events {
        BEFORE_REPLY, AFTER_REPLY
    }
}