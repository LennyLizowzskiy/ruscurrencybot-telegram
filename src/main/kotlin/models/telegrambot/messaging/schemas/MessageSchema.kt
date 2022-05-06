package models.telegrambot.messaging.schemas

/**
 * Схема отсылаемого ботом сообщения
 *
 * Может быть использована при помощи паттернов по типу %X, где X - любой удобный идентификатор
 *
 * Для форматирования текста в итоговом сообщении используется HTML-разметка
 */
value class MessageSchema(private val value: String) : Schematic {
    /**
     * Применить представленные параметры к схеме
     *
     * @param[parameters] Vararg из [Pair], где [Pair.first] - идентификатор паттерна, а [Pair.second] - то, на что нужно заменить паттерн
     * @return [String]
     */
    override fun applyParameters(vararg parameters: Pair<String, dynamic>): String {
        var local: String = value
        parameters.forEach{
            val index = "%" + it.first

            if (!local.contains(index))
                throw NoSuchElementException("$index was expected to be in input schema, not found")

            local = local.replace(index, it.second.toString(), true)
        }
        return local
    }

    override fun toString() = value

    companion object {
        private val store = mutableMapOf<String, MessageSchema>()

        /**
         * Регистрирует [MessageSchema] в системе
         *
         * @see MessageSchema
         */
        fun register(name: String, value: String) {
            store[name] = MessageSchema(value)
        }

        fun findByName(name: String) = store[name]
    }
}