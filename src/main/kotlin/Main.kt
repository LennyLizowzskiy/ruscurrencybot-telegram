import converters.MOEX
import converters.QIWI
import javascript.Chromium
import javascript.dependencies.applyJsDependenciesSettings
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import telegrambot.commands.registerChatCommands
import telegrambot.commands.registerInlineRequests
import telegrambot.listenChatCommands
import telegrambot.listenInlineQueries
import telegrambot.messages.schemas.registerMessageSchemas
import telegrambot.setupTelegramBot

suspend fun main() {
    applyJsDependenciesSettings()

    Chromium.initiate().await()

    MOEX.startAutoUpdater()
    QIWI.startAutoUpdater()

    // Даже после переписывания на корутины всё ещё на задержках
    // для await вместо этого нужен костыль, который не считаю нормальным
    delay(7000)

    registerMessageSchemas()

    registerChatCommands()
    registerInlineRequests()

    setupTelegramBot()
    listenChatCommands()
    listenInlineQueries()

    console.log("Listening...")
}