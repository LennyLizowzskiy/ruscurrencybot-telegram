import converters.MOEX
import converters.QIWI
import kotlin.js.Date

object CurrencyUpdater {
    var lastUpdateTime: Double = 0.0

    fun start(): dynamic {
        lastUpdateTime = Date.now()

        MOEX.updateCurrencyRates()
        QIWI.updateCurrencyRates()

        return setInterval({
            MOEX.updateCurrencyRates()
            QIWI.updateCurrencyRates()

            lastUpdateTime = Date.now()
        }, 3600000)
    }
}