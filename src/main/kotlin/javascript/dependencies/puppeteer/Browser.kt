package javascript.dependencies.puppeteer

import kotlin.js.Json
import kotlin.js.Promise

external interface Browser {
    fun close(): Promise<Unit>
    fun disconnect() // не убивает браузер, но отключает дебаггер (puppeteer)

    fun isConnected(): Boolean

    fun defaultBrowserContext(): Promise<BrowserContext>
    fun createIncognitoBrowserContext(options: Json): Promise<BrowserContext>

    fun newPage(): Promise<Page>

    fun pages(): Promise<Array<Page>>
}