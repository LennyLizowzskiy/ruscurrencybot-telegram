package models.telegrambot.messages.context

import models.telegrambot.messages.response.InlineQuery

class InlineContext : Context() {
    @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
    var query: InlineQuery?
        get() = get("query") as? InlineQuery
        set(value) = put("query", value)

    var schemaFillers: Array<Array<Pair<String, dynamic>>> = emptyArray()

    lateinit var matched: MatchResult
}