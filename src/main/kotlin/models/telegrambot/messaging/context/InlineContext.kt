package models.telegrambot.messaging.context

import models.telegrambot.messaging.response.InlineQuery

class InlineContext : Context() {
    @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
    var query: InlineQuery?
        get() = get("query") as? InlineQuery
        set(value) = put("query", value).unsafeCast<Unit>()

    var schemaFillers: Array<Array<Pair<String, dynamic>>> = emptyArray()

    var matched: MatchResult? = null
}