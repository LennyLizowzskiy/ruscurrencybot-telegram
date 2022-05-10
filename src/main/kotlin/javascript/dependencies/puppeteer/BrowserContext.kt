package javascript.dependencies.puppeteer

import kotlin.js.Promise

external interface BrowserContext {
    fun once(eventName: String, handler: () -> Unit)
    fun on(eventName: String, handler: () -> Unit)
    fun off(eventName: String, handler: () -> Unit)

    fun browser(): Browser

    fun clearPermissionOverrides(): Promise<Unit>

    fun close(): Promise<Unit>

    fun newPage(): Promise<Page>

    fun overridePermissions(origin: String, permissions: Array<String>)

    fun pages(): Promise<Array<Page>>
}