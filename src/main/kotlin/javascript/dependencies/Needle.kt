package javascript.dependencies

import javascript.stdlib.require
import kotlin.js.Json
import kotlin.js.Promise

private typealias Callback = (dynamic, dynamic) -> Unit


object NeedleMod {
    val fn = require("needle")

    val obj = Needle
}

@JsModule("needle")
@JsNonModule
external fun needle(
    methodName: String,
    url: String,
    data: Json = definedExternally,
    options: Json = definedExternally,
//    callback: Callback = definedExternally
): Promise<dynamic>

@JsModule("needle")
@JsNonModule
external object Needle {
    fun defaults(options: Json): Unit

    fun head(url: String, options: Json = definedExternally, callback: Callback  = definedExternally)
    fun get(url: String, options: Json = definedExternally, callback: Callback = definedExternally)
    fun post(url: String, data: Json, options: Json = definedExternally, callback: Callback = definedExternally)
    fun put(url: String, data: Json, options: Json = definedExternally, callback: Callback = definedExternally)
    fun patch(url: String, data: Json, options: Json = definedExternally, callback: Callback = definedExternally)
    fun delete(url: String, data: Json, options: Json = definedExternally, callback: Callback = definedExternally)

    fun request(methodName: String, url: String, data: Json, options: Json = definedExternally, callback: Callback = definedExternally)
}