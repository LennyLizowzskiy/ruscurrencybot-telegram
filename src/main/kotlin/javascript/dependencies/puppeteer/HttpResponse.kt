package javascript.dependencies.puppeteer

import kotlin.js.Json
import kotlin.js.Promise

external interface HttpResponse {
    fun json(): Promise<Json>
    fun text(): Promise<String>

    fun url(): String

    fun ok(): Boolean
    fun status(): Int
}