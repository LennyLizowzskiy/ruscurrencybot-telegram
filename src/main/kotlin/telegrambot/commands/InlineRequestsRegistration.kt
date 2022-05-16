package telegrambot.commands

import converters.AliExpress
import converters.MOEX
import converters.QIWI
import converters.misc.cutToTwoDecimalPlaces
import javascript.Timestamper
import models.converters.Currency
import models.telegrambot.messaging.commands.InlineRequest
import models.telegrambot.messaging.inlineresults.InlineQueryResultArticle
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputTextMessageContent
import models.telegrambot.messaging.schemas.MessageSchema

fun registerInlineRequests(): Unit = with(InlineRequest) {
    register("default", arrayOf(
        Regex("^Ø")
    )) {
        executeBeforeReply {
            val qiwiKZT = QIWI.getCurrencyByCharCode("KZT")
            val qiwiUSD = QIWI.getCurrencyByCharCode("USD")
            val qiwiEUR = QIWI.getCurrencyByCharCode("EUR")
            val qiwiCNY = QIWI.getCurrencyByCharCode("CNY")

            context.schemaFillers = arrayOf(
                arrayOf(
                    "converterExName" to MOEX.shortName, "exLastUpdateTimestamp" to Timestamper.getPrettyPrintedTime(MOEX.lastUpdateTime),
                    "kztM" to MOEX.getCurrencyByCharCode("KZT").second.sellingFor,
                    "usdM" to MOEX.getCurrencyByCharCode("USD").second.sellingFor,
                    "eurM" to MOEX.getCurrencyByCharCode("EUR").second.sellingFor,
                    "cnyM" to MOEX.getCurrencyByCharCode("CNY").second.sellingFor,

                    "converterAliName" to AliExpress.name, "aliLastUpdateTimestamp" to Timestamper.getPrettyPrintedTime(AliExpress.lastUpdateTime),
                    "usdA" to AliExpress.getCurrencyByCharCode("USD").second.sellingFor,

                    "converterBankName" to QIWI.shortName, "bankLastUpdateTimestamp" to Timestamper.getPrettyPrintedTime(QIWI.lastUpdateTime),
                    if (MOEX.isClosedNow())
                        "closedWarning" to "\n" + MessageSchema.findByName("moex_closed_warning").toString() + "\n"
                    else
                        "closedWarning" to "",
                    "kztBSell" to qiwiKZT.second.sellingFor, "kztBBuy" to qiwiKZT.second.buyingFor,
                    "usdBSell" to qiwiUSD.second.sellingFor, "usdBBuy" to qiwiUSD.second.buyingFor,
                    "eurBSell" to qiwiEUR.second.sellingFor, "eurBBuy" to qiwiEUR.second.buyingFor,
                    "cnyBSell" to qiwiCNY.second.sellingFor, "cnyBBuy" to qiwiCNY.second.buyingFor,
                )
            )
        }

        answer {
            addQueryResult(
                InlineQueryResultArticle(
                    title = "Отправить текущий курс валют в чат",
                    description = "Источники: Мосбиржа, AliExpress, QIWI",
                    input_message_content = InputTextMessageContent(
                        message_text = MessageSchema.findByName("current_exchange_rates")!!.applyParameters(*context.schemaFillers[0])
                    )
                )
            )
        }
    }

    register("convert", arrayOf(
        Regex("[\\d,.]+\\s\\w+")
    )) {
        executeBeforeReply {
            if (context.matched == null) {
                context["isInvalidResponse"] = true

                return@executeBeforeReply
            }

            val amount = Regex("[\\d,.]+").find(context.matched!!.value)?.value?.replace(',', '.')?.toDouble()
            val currency = Currency.findByCharCode(
                Regex("[A-Z]+").find(context.matched!!.value.uppercase())?.value!!
            )

            if (amount == null || currency == null || currency.charCode == "RUB") {
                context["isInvalidResponse"] = true

                return@executeBeforeReply
            }

            val (_, qiwiRates) = QIWI.getCurrencyByCharCode(currency.charCode)

            context["sourceAmount"] = amount
            context["sourceCurrency"] = currency.charCode

            val firstSchemaList: MutableList<Pair<String, dynamic>> = mutableListOf(
                "sourceAmount" to amount, "sourceCurrency" to currency.charCode,

                "moexName" to MOEX.name,
                "moexSourceRate" to (MOEX.getCurrencyByCharCode(currency.charCode).second.sellingFor * amount).cutToTwoDecimalPlaces(),

                "qiwiName" to QIWI.name,
                "qiwiSourceSellRate" to (qiwiRates.sellingFor * amount).cutToTwoDecimalPlaces(),
                "qiwiSourceBuyRate" to (qiwiRates.buyingFor * amount).cutToTwoDecimalPlaces()
            )

            if (currency.charCode == "USD") {
                firstSchemaList.add("aliName" to AliExpress.name)
                firstSchemaList.add("aliSourceRate" to (AliExpress.getCurrencyByCharCode("USD").second.sellingFor * amount).cutToTwoDecimalPlaces())
            }

            context.schemaFillers = arrayOf(
                firstSchemaList.toTypedArray()
            )
        }

        answer {
            addQueryResult(
                if (context["isInvalidResponse"] == true)
                    InlineQueryResultArticle(
                        title = "Введена неверная исходная валюта",
                        description = "Укажите одну из следующих: USD, EUR, KZT, CNY",
                        input_message_content = InputTextMessageContent(
                            message_text = "Я был слишком любопытным, за что поплатился жизнью (гуро)"
                        )
                    )
                else
                    InlineQueryResultArticle(
                        title = "Конвертировать ${context["sourceAmount"]} ${context["sourceCurrency"]} в RUB",
                        description = "по актуальным курсам валют",
                        input_message_content = InputTextMessageContent(
                            message_text = MessageSchema
                                .findByName(
                                    if (context["sourceCurrency"] == "USD")
                                        "oneCurrencyConverter_usd"
                                    else
                                        "oneCurrencyConverter_other")
                                !!.applyParameters(*context.schemaFillers[0])
                        )
                    )
            )
        }
    }
}