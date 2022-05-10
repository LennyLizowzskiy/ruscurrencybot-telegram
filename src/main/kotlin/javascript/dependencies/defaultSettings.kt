package javascript.dependencies

import ResourceStore
import kotlin.js.json

fun applyJsDependenciesSettings() {
    Needle.defaults(json(
        "parse_response" to false,
        "user_agent" to ResourceStore.network["user_agent"]!!
    ))
}