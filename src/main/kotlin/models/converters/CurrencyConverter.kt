package models.converters

import javascript.Timestamper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlin.js.Date
import kotlin.js.Promise

sealed class CurrencyConverter {
    abstract val name: String
    abstract val shortName: String
    abstract val baseUrl: String

    val baseCurrency = Currency.Ruble

    val latestRequestedData: MutableSet<CurrencyFull> = mutableSetOf()

    @OptIn(DelicateCoroutinesApi::class)
    open suspend fun initiate(): Promise<*> = GlobalScope.promise {}

    abstract suspend fun updateCurrencyRates(): Promise<*>

    fun getCurrencyByCharCode(charCode: String)
        = latestRequestedData.find { it.first.charCode == charCode } ?: throw NoSuchElementException("tried to find $charCode in latestRequestedData, not found")

    /**
     * Конвертирует рубли в указанную валюту
     *
     * @return [Float]
     */
    abstract fun convertRublesTo(to: Currency, amount: Float, transactionType: ConvertType?): Float

    /**
     * Конвертирует указанную валюту в рубли
     *
     * @return [Float]
     */
    abstract fun convertToRubles(from: Currency, amount: Float, transactionType: ConvertType?): Float


    // Автообновление курсов

    abstract var updateInterval: Long

    var updaterActive = true
    private var noUpdateHours: Set<String> = emptySet() // пример: "00", "09", "14"
    var lastUpdateTime: Double = 0.0
    fun disableUpdatesAt(vararg hours: String) {
        noUpdateHours = noUpdateHours.plus(hours.asIterable())
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun startAutoUpdater() = GlobalScope.launch {
        initiate().await()

        while (updaterActive) {
            Date.now().let { ms ->
                Timestamper.format(ms, "HH").unsafeCast<String>().also { currentHour ->
                    if (noUpdateHours.any { it == currentHour } && lastUpdateTime != 0.0)
                        delay(updateInterval)
                }
            }

            updateCurrencyRates().await()

            lastUpdateTime = Date.now()

            delay(updateInterval)
        }
    }
}

/**
 * Биржевой конвертер валют
 */
abstract class ExchangeCurrencyConverter: CurrencyConverter() {
    override fun convertRublesTo(to: Currency, amount: Float, transactionType: ConvertType?)
        = with(getCurrencyByCharCode(to.charCode)) { amount / this.second.sellingFor * this.first.nominal }

    override fun convertToRubles(from: Currency, amount: Float, transactionType: ConvertType?)
        = with(getCurrencyByCharCode(from.charCode)) { amount / this.first.nominal * this.second.sellingFor }
}

/**
 * Банковский конвертер валют (со спредом продажа-покупка)
 */
abstract class BankCurrencyConverter: CurrencyConverter() {
    override fun convertRublesTo(to: Currency, amount: Float, transactionType: ConvertType?): Float
        = with(getCurrencyByCharCode(to.charCode)) {
            return when(transactionType) {
                ConvertType.SELLING -> amount / this.second.sellingFor
                ConvertType.BUYING, null -> amount / this.second.buyingFor
            } * this.first.nominal
        }

    override fun convertToRubles(from: Currency, amount: Float, transactionType: ConvertType?): Float
        = with(getCurrencyByCharCode(from.charCode)){
            return when (transactionType) {
                ConvertType.SELLING -> amount / this.first.nominal * this.second.sellingFor
                ConvertType.BUYING, null -> amount / this.first.nominal * this.second.buyingFor
            }
        }
}

enum class ConvertType {
    SELLING, BUYING
}