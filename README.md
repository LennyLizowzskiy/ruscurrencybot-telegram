## Telegram-бот для отслеживания актуального курса валют на базе Kotlin/JS

### ⚠ Собирать только в Intellij IDEA ⚠

Нормальное README будет сделано позже...

### Как добавить свои команды:
1) Добавить [в этот файл](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/messages/schemas/MessageSchemaRegistration.kt) необходимые схемы для заполнения при отправке
2) Добавить чат-команду в [ChatCommandsRegistration.kt](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/commands/ChatCommandsRegistration.kt) или ответ на инлайн-запрос в [InlineRequestsRegistration](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/commands/InlineRequestsRegistration.kt)

### Как собрать билд бота из исходников:
1) Установить IntelliJ IDEA
2) Импортировать проект с помощью ссылки на **.git** в данном репозитории
3) Установить в ENV-переменную **TELEGRAM_BOT_API_KEY** свой ключ
4) Запустить **build** или **nodeRun** (первое - просто сборка, второе - сборка и запуск)

### На хостинг ботов
https://yoomoney.ru/to/410014255874092