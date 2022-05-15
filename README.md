## Telegram-бот для отслеживания актуального курса валют на базе Kotlin/JS

Нормальное README будет сделано позже...

### Как добавить свои команды:
1) Добавить [в этот файл](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/messages/schemas/MessageSchemaRegistration.kt) необходимые схемы для заполнения при отправке
2) Добавить чат-команду в [ChatCommandsRegistration.kt](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/commands/ChatCommandsRegistration.kt) или ответ на инлайн-запрос в [InlineRequestsRegistration.kt](https://github.com/LennyLizowzskiy/ruscurrencybot-telegram/blob/master/src/main/kotlin/telegrambot/commands/InlineRequestsRegistration.kt)

### Как собрать билд бота из исходников:
1) Установить JDK 8 (если собираете на Windows, то Java скорее всего уже установлена)
2) В командной строке прописать и подождать до полной сборки: `./gradlew build`
3) Установить в переменную окружения (environment variable) `TELEGRAM_BOT_API_KEY` ключ, который был получен в [@BotFather](https://t.me/botfather)
4) Теперь папка `build` может быть экспортирована и запущена где угодно. Команда для запуска в терминале, открытого в данной папке: `node ./js/packages/RusCurrencyConverterTGBot`. 

Если при сборке вылетает ошибка, то можно собрать в IntelliJ IDEA.

#
### На хостинг ботов
https://yoomoney.ru/to/410014255874092