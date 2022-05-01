package converters

import javascript.Timestamper
import models.converters.BankCurrencyConverter
import models.converters.ConvertType
import models.converters.Currency
import models.converters.CurrencyRate
import pseudoBrowser
import kotlin.js.json

object QIWI : BankCurrencyConverter() {
    override val name = "QIWI Кошелёк"
    override val shortName = "QIWI"

    override val baseUrl = "https://qiwi.com/payment/exchange"

    val page: dynamic = pseudoBrowser.then { browser -> browser.newPage() }.then { _page -> _page.goto(baseUrl).then { _page } }

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

    override fun updateCurrencyRates(): dynamic {
        latestRequestedData.clear()

        return page.then { _page ->
            _page.reload(json(
                "waitUntil" to arrayOf("networkidle0", "domcontentloaded")
            )).then { _ ->
                _page.content().then promise@{ html ->
                    val matched = Regex("(?<=<p class=\"css-k8xqk1\">)[^<]+", RegexOption.MULTILINE).findAll(html.toString())
                    val regexCharCode = Regex("[A-Z]+")

                    matched.forEachIndexed { index, _value ->
                        // indexes for: amount charCode, buyingFor, sellingFor
                        //
                        // ID    ACC  BF   SF
                        // KZT - 0    1    2
                        // USD - 3    4    5
                        // EUR - 6    7    8
                        // CNY - 9    10   11

                        val value = _value.value

                        when(index) {
                            0, 3, 6, 9 -> {
                                latestRequestedData.add(
                                    Currency.findByCharCode(regexCharCode.find(value)!!.value)!!
                                            to CurrencyRate(
                                                matched.elementAt(index+2).value.toFloat(),
                                                matched.elementAt(index+1).value.toFloat()
                                            )
                                )
                            }
                        }
                    }

                    console.log("QIWI data updated at " + Timestamper.getPrettyPrintedCurrentTime())

                    return@promise latestRequestedData
                }
            }
        }
    }
}