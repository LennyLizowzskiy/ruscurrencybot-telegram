package converters

import javascript.Timestamper
import javascript.dependencies.needle
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import models.converters.Currency
import models.converters.CurrencyRate
import models.converters.ExchangeCurrencyConverter

object AliExpress : ExchangeCurrencyConverter() {
    override val name = "AliExpress"
    override val shortName = "AliExpress"
    override val baseUrl = "https://alicoup.ru/currency/"

    override var updateInterval: Long = 43200000 // 12h

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun updateCurrencyRates() = GlobalScope.promise {
        needle("get", baseUrl).then { response: dynamic ->
            val regex = Regex("\\d+\\.\\d+(?=\\sруб)", RegexOption.MULTILINE)

            latestRequestedData.add(
                Currency.findByCharCode("USD")!!
                    to
                        CurrencyRate(
                            regex.findAll(response.body.unsafeCast<String>())
                                .elementAt(1).value.toFloat()
                        )
            )

            console.log("$shortName data updated at " + Timestamper.getPrettyPrintedCurrentTime())
        }.await()
    }
}