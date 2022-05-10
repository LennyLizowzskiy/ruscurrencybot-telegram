package javascript

import javascript.dependencies.puppeteer.Browser
import javascript.dependencies.puppeteer.Puppeteer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import kotlin.js.Json


object Chromium {
    lateinit var value: Browser

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun initiate() = GlobalScope.promise {
        Puppeteer.launch().then { value = it }.await()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun initiate(options: Json) = GlobalScope.promise {
        Puppeteer.launch(options).then { value = it }
    }
}