package converters

import javascript.Timestamper
import javascript.entriesOf
import javascript.mapOf
import models.converters.ConvertType
import models.converters.Currency
import models.converters.CurrencyRate
import models.converters.ExchangeCurrencyConverter
import needle
import xmlConverter
import kotlin.js.Date
import kotlin.js.json

object MOEX : ExchangeCurrencyConverter() {
    override val name = "Московская биржжа"
    override val shortName = "MOEX"

    override val baseUrl = "http://www.cbr.ru/scripts/XML_daily.asp"

    override fun convertRublesTo(to: Currency, amount: Float, transactionType: ConvertType?)
        = with(getCurrencyByCharCode(to.charCode)) { amount / this.second.sellingFor * this.first.nominal }

    override fun convertToRubles(from: Currency, amount: Float, transactionType: ConvertType?)
        = with(getCurrencyByCharCode(from.charCode)) { amount / this.first.nominal * this.second.sellingFor }

    fun isClosedNow(): Boolean = with(Date(Date.now() + 3600 * 1000 * 3)) {
        return this.getHours() > 18 || this.getHours() < 10
    }

    override fun updateCurrencyRates(): dynamic {
        latestRequestedData.clear()

        return needle("get", baseUrl, json(
            "content_type" to "application/xml"
        )).then promise@{ response ->
            val cbrJs = mapOf(xmlConverter.xml2js(response.body, json(
                "compact" to true
            )))

            for ((_, _value) in entriesOf(cbrJs["ValCurs"].asDynamic()["Valute"])) {
                val value = _value.asDynamic()

                val currency: Currency = Currency.findByCharCode(value["CharCode"]["_text"].toString()) ?: continue

                latestRequestedData.add(
                    currency to CurrencyRate(
                        value["Value"]["_text"].toString().replace(',', '.').toFloat(), null)
                )
            }

            console.log("MOEX data updated at " + Timestamper.getPrettyPrintedCurrentTime())

            return@promise latestRequestedData
        }
    }
}