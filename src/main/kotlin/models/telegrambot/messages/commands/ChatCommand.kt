package models.telegrambot.messages.commands

import models.telegrambot.messages.context.ChatContext

class ChatCommand : Command() {
    lateinit var reply: ChatCommandReply

    override val context = ChatContext()

    // бойлерплейт, ага
    fun reply(replyBuilder: ChatCommandReply.() -> Unit) {
        executables[Events.BEFORE_REPLY]!!()
        reply = ChatCommandReply(context).apply(replyBuilder)
        executables[Events.AFTER_REPLY]!!()
    }

    companion object : CommandCompanion<ChatCommand> {
        override val storage = mutableMapOf<String, ChatCommand>()

        fun register(name: String, logic: ChatCommand.() -> Unit) {
            storage.put(name.lowercase(), ChatCommand().apply(logic))
        }
    }
}