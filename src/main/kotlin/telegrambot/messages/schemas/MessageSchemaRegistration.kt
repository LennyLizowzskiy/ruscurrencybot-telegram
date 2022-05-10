package telegrambot.messages.schemas

import models.telegrambot.messaging.schemas.MessageSchema

fun registerMessageSchemas(): Unit = with(MessageSchema) {
    register(
        "current_exchange_rates",
        """
        <b>Текущий курс обмена валют</b>
        
        <b>%converterExName от %exLastUpdateTimestamp:</b>
        🇰🇿 100 KZT за %kztM RUB
        🇺🇸 1 USD за %usdM RUB
        🇪🇺 1 EUR за %eurM RUB
        🇨🇳 1 CNY за %cnyM RUB
          
        <b>%converterBankName (покупка/продажа) от %bankLastUpdateTimestamp:</b> %closedWarning
        🇰🇿 100 KZT за %kztBBuy/%kztBSell RUB
        🇺🇸 1 USD за %usdBBuy/%usdBSell RUB
        🇪🇺 1 EUR за %eurBBuy/%eurBSell RUB
        🇨🇳 1 CNY за %cnyBBuy/%cnyBSell RUB
        """.trimIndent()
    )

    register(
        "moex_closed_warning",
        """
        ⚠️ <b>Заградительный курс!</b>
        ⚠️ Все банки делают курс обмена менее выгодным, когда MOEX не работает
        ⚠️ Часы работы валютного сектора MOEX: с 10:00 по 18:59
        """.trimIndent()
    )
}