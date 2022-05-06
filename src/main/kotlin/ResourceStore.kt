import kotlin.js.json

object ResourceStore {
    val sensitiveInformation = json(
        "authorization" to json(
            "telegram_bot_api_key" to process.env.TELEGRAM_BOT_API_KEY
        )
    )

    val network = json(
        "user_agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.4985.0 Safari/537.36 Edg/102.0.1236.0"
    )

    val telegramBotDefaults = json(
        "parse_mode" to "HTML"
    )
}