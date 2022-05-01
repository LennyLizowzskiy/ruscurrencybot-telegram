package models.telegrambot.messages.commands

import models.telegrambot.messages.context.InlineContext

class InlineAnswer(override val context: InlineContext) : CommandReply(context) {
    // TODO: Переписать dynamic на [models.telegrambot.messages.inline_results.InlineQueryResultItem]
    var results: Array<InlineQueryResult> = emptyArray()

    fun addQueryResult(block: InlineQueryResult.() -> Unit) {
        results += InlineQueryResult().apply(block).apply {
            if (this@InlineAnswer.options.autoAssignIds)
                this.id = this@InlineAnswer.hashCode().toString() + "_" + results.size.toString()
        }
    }

    var options = Options()

    /**
     * Доступные настройки:
     *
     * autoAssignIds - автоматическая выдача поля id в порядке возрастания и добавления инлайн-ответов в код
     *
     * cacheTime - время кэширования ответа
     *
     * isPersonal - кэширование лично для юзера или нет
     *
     * nextOffset - сколько нужно ещё отослать байт, чтобы получить другой ответ
     *
     * switchPmText - должна ли показываться кнопка перехода в ЛС бота
     *
     * switchPmParameter - неявные данные, которые будут переданы при нажатии на /start в боте
     */
    fun setOptions(block: Options.() -> Unit) {
        options = Options().apply(block)
    }

    class Options(
        val autoAssignIds: Boolean = true,
        val cacheTime: Int? = null,
        val isPersonal: Boolean? = null,
        val nextOffset: String? = null,
        val switchPmText: String? = null,
        val switchPmParameter: String? = null
    )
}

class InlineQueryResult() {
    lateinit var type: String
    lateinit var id: String
    lateinit var additions: Map<String, Any>
}