import converters.AliExpress
import converters.MOEX
import converters.QIWI
import javascript.dependencies.applyJsDependenciesSettings
import kotlinx.coroutines.await
import telegrambot.commands.registerChatCommands
import telegrambot.commands.registerInlineRequests
import telegrambot.listenChatCommands
import telegrambot.listenInlineQueries
import telegrambot.messages.schemas.registerMessageSchemas
import telegrambot.setupTelegramBot
import kotlin.js.Promise

suspend fun main() {
    applyJsDependenciesSettings()

    MOEX.startAutoUpdater()
    QIWI.startAutoUpdater()
    AliExpress.startAutoUpdater()

    Promise.all(
        arrayOf(
            MOEX.awaitFirstUpdate(),
            QIWI.awaitFirstUpdate(),
            AliExpress.awaitFirstUpdate()
        )
    ).await()

    registerMessageSchemas()

    registerChatCommands()
    registerInlineRequests()

    setupTelegramBot()
    listenChatCommands()
    listenInlineQueries()

    console.log("Listening...")
}