package telegrambot.commands

import CurrencyUpdater.lastUpdateTime
import converters.MOEX
import converters.QIWI
import javascript.Timestamper
import models.telegrambot.messaging.commands.InlineRequest
import models.telegrambot.messaging.inlineresults.InlineQueryResultArticle
import models.telegrambot.messaging.inlineresults.inputmessagecontents.InputTextMessageContent
import models.telegrambot.messaging.schemas.MessageSchema

fun registerInlineRequests(): Unit = with(InlineRequest) {
    register("default", arrayOf(
        Regex(".*")
    )) {
        executeBeforeReply {
            val qiwiKZT = QIWI.getCurrencyByCharCode("KZT")
            val qiwiUSD = QIWI.getCurrencyByCharCode("USD")
            val qiwiEUR = QIWI.getCurrencyByCharCode("EUR")
            val qiwiCNY = QIWI.getCurrencyByCharCode("CNY")

            context.schemaFillers = arrayOf(
                arrayOf(
                    "converterExName" to MOEX.shortName,
                    "kztM" to MOEX.getCurrencyByCharCode("KZT").second.sellingFor,
                    "usdM" to MOEX.getCurrencyByCharCode("USD").second.sellingFor,
                    "eurM" to MOEX.getCurrencyByCharCode("EUR").second.sellingFor,
                    "cnyM" to MOEX.getCurrencyByCharCode("CNY").second.sellingFor,

                    "converterBankName" to QIWI.shortName,
                    if (MOEX.isClosedNow())
                        "closedWarning" to "\n" + MessageSchema.findByName("moex_closed_warning").toString() + "\n"
                    else
                        "closedWarning" to "",
                    "kztBSell" to qiwiKZT.second.sellingFor, "kztBBuy" to qiwiKZT.second.buyingFor,
                    "usdBSell" to qiwiUSD.second.sellingFor, "usdBBuy" to qiwiUSD.second.buyingFor,
                    "eurBSell" to qiwiEUR.second.sellingFor, "eurBBuy" to qiwiEUR.second.buyingFor,
                    "cnyBSell" to qiwiCNY.second.sellingFor, "cnyBBuy" to qiwiCNY.second.buyingFor,

                    "time" to Timestamper.getPrettyPrintedTime(lastUpdateTime)
                )
            )
        }

        answer {
            addQueryResult(
                InlineQueryResultArticle(
                    title = "Отправить текущий курс валют в чат",
                    description = "Источники: Мосбиржа, QIWI",
                    input_message_content = InputTextMessageContent(
                        message_text = MessageSchema.findByName("current_exchange_rates")!!.applyParameters(*context.schemaFillers[0])
                    )
                )
            )
        }
    }
}