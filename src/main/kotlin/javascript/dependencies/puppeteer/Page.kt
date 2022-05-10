package javascript.dependencies.puppeteer

import kotlin.js.Json
import kotlin.js.Promise

external interface Page {
    fun once(eventName: String, handler: () -> Unit)
    fun on(eventName: String, handler: () -> Unit)
    fun off(eventName: String, handler: () -> Unit)

    fun browser(): Promise<Browser>

    fun browserContext(): Promise<BrowserContext>

    fun close(): Promise<Unit>
    fun close(options: Json): Promise<Unit>

    fun goto(url: String, options: Json = definedExternally): Promise<HttpResponse?>

    fun reload(options: Json = definedExternally): Promise<HttpResponse>

    fun setJavaScriptEnabled(enabled: Boolean): Promise<Unit>

    fun isClosed(): Boolean

    fun isJavaScriptEnabled(): Boolean

    fun content(): Promise<String>
}