## Telegram-бот для отслеживания актуального курса валют на базе Kotlin/JS

### ⚠ Собирать только в Intellij IDEA ⚠

Нормальное README будет сделано позже...

### Как добавить свои команды:
1) Добавить [в этот файл](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/messages/schemas/MessageSchemaRegistration.kt) необходимые схемы для заполнения при отправке
2) Добавить чат-команду в [ChatCommandsRegistration.kt](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/commands/ChatCommandsRegistration.kt) или ответ на инлайн-запрос в [InlineRequestsRegistration.kt](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/commands/InlineRequestsRegistration.kt)

### Как собрать билд бота из исходников:
1) Установить JDK 8 (если собираете на Windows, то Java скорее всего уже установлена)
2) В командной строке прописать и подождать до полной сборки: `./gradlew build`
3) В сгенерированной папке `/build` пройти по пути `/js/node_modules/puppeteer`
4) Открыть терминал в данной папке и прописать `npm install`, затем ождаться полной установки модуля
5) Установить в переменную окружения (environment variable) `TELEGRAM_BOT_API_KEY` ключ, который был получен в [@BotFather](https://t.me/botfather)
6) Теперь папка `build` может быть экспортирована и запущена где угодно. Команда для запуска в терминале, открытого в данной папке: `node ./packages/RusCurrencyConverterTGBot`


#
### На хостинг ботов
https://yoomoney.ru/to/410014255874092