package models.telegrambot.messaging.commands

import models.telegrambot.messaging.context.InlineContext

class InlineRequest : Command() {
    lateinit var regexes: Array<Regex>

    override var context = InlineContext()

    // бойлерплейт, ага
    fun answer(answerBuilder: InlineAnswer.() -> Unit) {
        executables[Events.ANSWER] =
            fun() = InlineAnswer(context).apply(answerBuilder)
    }

    companion object : CommandCompanion<InlineRequest> {
        override val storage: MutableMap<String, InlineRequest> = mutableMapOf()

        fun register(name: String, regexes: Array<Regex>, logic: InlineRequest.() -> Unit) {
            storage[name] = InlineRequest().apply(logic).apply {
                this.regexes = regexes
            }
        }
    }
}