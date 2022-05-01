import telegrambot.commands.registerChatCommands
import telegrambot.commands.registerInlineRequests
import telegrambot.listenChatCommands
import telegrambot.listenInlineQueries
import telegrambot.messages.schemas.registerMessageSchemas
import telegrambot.setupTelegramBot

fun main() {
    applyJsDependenciesSettings()

    CurrencyUpdater.start()

    // Тут мне было лень реализовывать адекватное решение для ожидания загрузки курсов
    // Если переписать всё на внешние интерфейсы (в т.ч. на Promise<T>) вместо [dynamic],
    // то можно будет без костылей просто поставить then или вообще в suspend fun пихнуть
    setTimeout({
        registerMessageSchemas()

        registerChatCommands()
        registerInlineRequests()

        setupTelegramBot()
        listenChatCommands()
        listenInlineQueries()

        console.log("Listening...")
    }, 10000)
}