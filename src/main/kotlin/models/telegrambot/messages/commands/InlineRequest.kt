package models.telegrambot.messages.commands

import models.telegrambot.messages.context.InlineContext

class InlineRequest : Command() {
    lateinit var answer: InlineAnswer
    lateinit var regexes: Array<Regex>

    override val context = InlineContext()

    // бойлерплейт, ага
    fun answer(answerBuilder: InlineAnswer.() -> Unit) {
        executables[Events.BEFORE_REPLY]!!()
        answer = InlineAnswer(context).apply(answerBuilder)
        executables[Events.BEFORE_REPLY]!!()
    }

    companion object : CommandCompanion<InlineRequest> {
        override val storage = mutableMapOf<String, InlineRequest>()

        fun register(name: String, regexes: Array<Regex>, logic: InlineRequest.() -> Unit) {
            storage[name] = InlineRequest().apply(logic).apply {
                this.regexes = regexes
            }
        }
    }
}