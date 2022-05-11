package converters

import javascript.Timestamper
import javascript.dependencies.needle
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import models.converters.BankCurrencyConverter
import models.converters.Currency
import models.converters.CurrencyRate
import kotlin.math.roundToInt

@OptIn(DelicateCoroutinesApi::class)
object QIWI : BankCurrencyConverter() {
    override val name = "QIWI Кошелёк"
    override val shortName = "QIWI"

    override val baseUrl = "https://qiwi.com/payment/exchange"

    override var updateInterval: Long = 3600000

    fun roundFloat(number: Float): Float = (100.0f * number).roundToInt() / 100.0f

    override suspend fun updateCurrencyRates() = GlobalScope.promise {
        latestRequestedData.clear()

        // 398 - 643 (nominal = 100 / .rate) = KZT покупка
        // 643 - 398 = KZT продажа

        // 840 - 643 (nominal = 1 / .rate) = USD покупка
        // 643 - 840 = USD продажа

        // 978 - 643 (nominal / .rate) = EUR покупка
        // 643 - 978 = EUR продажа

        // 156 - 643 (nominal / .rate) = CNY покупка
        // 643 - 156 = CNY продажа
        needle("get", "https://edge.qiwi.com/sinap/crossRates").then { response: dynamic ->
            val result = js("JSON.parse(response.body).result").unsafeCast<Array<CrossRate>>()

            // АЛЁРТ
            // АЛЁРТ
            // АЛЁРТ
            // ОЧЕНЬ НЕОПТИМИЗИРОВАННЫЙ КОД
            val rubCode = QiwiCurrencyCodes.RUB.id

            QiwiCurrencyCodes.CNY.id.also { cnyCode ->
                val cnyBuy = result.find { it.from == cnyCode && it.to == rubCode }!!.rate
                val cnySell = result.find { it.from == rubCode && it.to == cnyCode }!!.rate

                val cnyCur = Currency.findByCharCode("CNY")!!

                latestRequestedData.add(
                    cnyCur to CurrencyRate(
                        roundFloat(cnyCur.nominal * cnySell),
                        roundFloat(cnyCur.nominal / cnyBuy)
                    )
                )
            }

            QiwiCurrencyCodes.KZT.id.also { kztCode ->
                val kztBuy = result.find { it.from == kztCode && it.to == rubCode }!!.rate
                val kztSell = result.find { it.from == rubCode && it.to == kztCode }!!.rate

                val kztCur = Currency.findByCharCode("KZT")!!

                latestRequestedData.add(
                    kztCur to CurrencyRate(
                        roundFloat(kztCur.nominal * kztSell),
                        roundFloat(kztCur.nominal / kztBuy)
                    )
                )
            }

            QiwiCurrencyCodes.USD.id.also { usdCode ->
                val usdBuy = result.find { it.from == usdCode && it.to == rubCode }!!.rate
                val usdSell = result.find { it.from == rubCode && it.to == usdCode }!!.rate

                val usdCur = Currency.findByCharCode("USD")!!

                latestRequestedData.add(
                    usdCur to CurrencyRate(
                        roundFloat(usdCur.nominal * usdSell),
                        roundFloat(usdCur.nominal / usdBuy)
                    )
                )
            }

            QiwiCurrencyCodes.EUR.id.also { eurCode ->
                val eurBuy = result.find { it.from == eurCode && it.to == rubCode }!!.rate
                val eurSell = result.find { it.from == rubCode && it.to == eurCode }!!.rate

                val eurCur = Currency.findByCharCode("EUR")!!

                latestRequestedData.add(
                    eurCur to CurrencyRate(
                        roundFloat(eurCur.nominal * eurSell),
                        roundFloat(eurCur.nominal / eurBuy)
                    )
                )
            }

            console.log("$shortName data updated at " + Timestamper.getPrettyPrintedCurrentTime())
        }.await()
    }
}

private external interface CrossRate {
    val set: String
    val from: String
    val to: String
    val rate: Float
}

private enum class QiwiCurrencyCodes(val id: String) {
    KZT("398"), USD("840"),
    EUR("978"), CNY("156"),
    RUB("643")
}