package models.converters

sealed class CurrencyConverter {
    abstract val name: String
    abstract val shortName: String
    abstract val baseUrl: String

    val baseCurrency = Currency.Ruble

    val latestRequestedData: MutableSet<CurrencyFull> = mutableSetOf()

    abstract fun updateCurrencyRates()

    fun getCurrencyByCharCode(charCode: String)
        = latestRequestedData.find { it.first.charCode == charCode } ?: throw NoSuchElementException("tried to find $charCode in latestRequestedData, not found")

    /**
     * Конвертирует рубли в указанную валюту
     *
     * @return [Float]
     */
    abstract fun convertRublesTo(to: Currency, amount: Float, transactionType: ConvertType?): Float
    // TODO: Учитывать данные о базовом номининале валюты (делить цену продажи/покупки на номинал)
    /**
     * Конвертирует указанную валюту в рубли
     *
     * @return [Float]
     */
    abstract fun convertToRubles(from: Currency, amount: Float, transactionType: ConvertType?): Float
}

/**
 * Биржевой конвертер валют
 */
abstract class ExchangeCurrencyConverter: CurrencyConverter()

/**
 * Банковский конвертер валют (со спредом продажа-покупка)
 */
abstract class BankCurrencyConverter: CurrencyConverter()

enum class ConvertType {
    SELLING, BUYING
}