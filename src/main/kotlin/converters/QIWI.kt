package converters

import javascript.Chromium
import javascript.Timestamper
import javascript.dependencies.puppeteer.Page
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import models.converters.BankCurrencyConverter
import models.converters.ConvertType
import models.converters.Currency
import models.converters.CurrencyRate
import kotlin.js.json

@OptIn(DelicateCoroutinesApi::class)
object QIWI : BankCurrencyConverter() {
    override val name = "QIWI Кошелёк"
    override val shortName = "QIWI"

    override val baseUrl = "https://qiwi.com/payment/exchange"

    lateinit var page: Page

    override suspend fun initiate() = GlobalScope.promise {
        Chromium.value.newPage().await().also {
            it.goto(baseUrl).await()

            page = it
        }
    }

    override var updateInterval: Long = 3600000

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun updateCurrencyRates() = GlobalScope.promise {
        latestRequestedData.clear()

        page.reload(json(
            "waitUntil" to arrayOf("networkidle0", "domcontentloaded")
        )).await()

        page.content().then { html ->
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
            console.log("$shortName data updated at " + Timestamper.getPrettyPrintedCurrentTime())
        }.await()
    }
}