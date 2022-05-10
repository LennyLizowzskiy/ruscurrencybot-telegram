package javascript.dependencies.puppeteer

import kotlin.js.Json
import kotlin.js.Promise

/**
 * Chromium в формате Node
 *
 * [Открыть на NPMJS](https://www.npmjs.com/package/puppeteer)
 */
@JsModule("puppeteer")
@JsNonModule
@JsName("puppeteer")
external object Puppeteer {
    fun defaultArgs(options: Json)

    fun launch(options: Json = definedExternally): Promise<Browser>
}