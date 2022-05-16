package models.converters

typealias ExchangeCurrency = Pair<Currency, CurrencyRate>

/*
 * Для заполнения рекомендуется использовать данную схему:
 * http://www.cbr.ru/scripts/XML_daily.asp
 */
enum class Currency(
    val charCode: String,
    val nameRu: String,
    val nominal: Int,
    val sign: Char
) {
    Ruble("RUB", "Российский рубль", 1, '₽'),
    Dollar("USD", "Доллар США", 1, '$'),
    Euro("EUR", "Евро", 1, '€'),
    Tenge("KZT", "Казахстанский тенге", 100, '₸'),
    Yuan("CNY", "Китайский юань", 1, '¥');

    companion object {
        fun findByCharCode(charCode: String) = Currency.values().find { it.charCode == charCode }
    }
}

class CurrencyRate(val sellingFor: Float, buyingFor: Float? = null) {
    private val _buyingFor = buyingFor
    val buyingFor: Float
        get() = _buyingFor ?: sellingFor
}