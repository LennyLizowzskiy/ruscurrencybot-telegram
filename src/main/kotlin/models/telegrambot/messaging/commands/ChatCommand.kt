package models.telegrambot.messaging.commands

import models.telegrambot.messaging.context.ChatContext

class ChatCommand : Command() {
    lateinit var reply: ChatCommandReply

    override var context = ChatContext()

    // бойлерплейт, ага
    fun reply(replyBuilder: ChatCommandReply.() -> Unit) {
        executables[Events.ANSWER] =
            fun() = ChatCommandReply(context).apply(replyBuilder)
    }

    companion object : CommandCompanion<ChatCommand> {
        override val storage = mutableMapOf<String, ChatCommand>()

        fun register(name: String, logic: ChatCommand.() -> Unit) {
            storage[name.lowercase()] = ChatCommand().apply(logic)
        }
    }
}