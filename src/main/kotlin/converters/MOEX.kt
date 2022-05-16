package converters

import javascript.JSOAdditions
import javascript.Timestamper
import javascript.dependencies.needle
import javascript.dependencies.xmlConverter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import models.converters.Currency
import models.converters.CurrencyRate
import models.converters.ExchangeCurrencyConverter
import kotlin.js.Date
import kotlin.js.json

object MOEX : ExchangeCurrencyConverter() {
    override val name = "Московская биржа"
    override val shortName = "MOEX"

    override val baseUrl = "http://www.cbr.ru/scripts/XML_daily.asp"

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun initiate() = GlobalScope.promise {
        disableUpdatesAt("19", "20", "21", "22", "23",
              "00", "01", "02", "03", "04", "05", "06", "07", "08", "09")
    }

    override var updateInterval: Long = 3600000

    fun isClosedNow(): Boolean = with(Date(Date.now())) date@{
        return this@date.getHours() > 17 || this@date.getHours() < 10
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun updateCurrencyRates() = GlobalScope.promise {
        latestRequestedData.clear()

        needle("get", baseUrl, json(
            "content_type" to "application/xml"
        )).then { response: dynamic ->
            val cbrJs = JSOAdditions.entriesOfAsMap(
                xmlConverter.xml2js(
                    response.body.unsafeCast<String>(),
                    json(
                        "compact" to true
                    )
                )
            )

            for ((_, _value) in JSOAdditions.entriesOf(cbrJs["ValCurs"].asDynamic()["Valute"])) {
                val value = _value.asDynamic()

                val currency: Currency = Currency.findByCharCode(value["CharCode"]["_text"].toString()) ?: continue

                latestRequestedData.add(
                    currency to CurrencyRate(
                        value["Value"]["_text"].toString().replace(',', '.').toFloat()
                    )
                )
            }

            console.log("$shortName data updated at " + Timestamper.getPrettyPrintedCurrentTime())
        }.await()
    }
}