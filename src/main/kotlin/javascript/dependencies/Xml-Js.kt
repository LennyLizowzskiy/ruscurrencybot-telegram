package javascript.dependencies

import kotlin.js.Json

@JsModule("xml-js")
@JsNonModule
external object xmlConverter {
    fun js2xml(js: Json, options: Json): String
    fun json2xml(json: String, options: Json): String
    fun xml2js(xml: String, options: Json): dynamic
    fun xml2json(xml: String, options: Json): String
}