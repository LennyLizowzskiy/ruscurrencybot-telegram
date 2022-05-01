package telegrambot.commands

import converters.MOEX
import converters.QIWI
import javascript.Timestamper
import models.telegrambot.messages.commands.InlineRequest
import models.telegrambot.messages.schemas.MessageSchema

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
                    Pair("converterExName", MOEX.shortName),
                    Pair("kztM", MOEX.getCurrencyByCharCode("KZT").second.sellingFor),
                    Pair("usdM", MOEX.getCurrencyByCharCode("USD").second.sellingFor),
                    Pair("eurM", MOEX.getCurrencyByCharCode("EUR").second.sellingFor),
                    Pair("cnyM", MOEX.getCurrencyByCharCode("CNY").second.sellingFor),

                    Pair("converterBankName", QIWI.shortName),
                    if (MOEX.isClosedNow())
                        Pair("closedWarning", "\n" + MessageSchema.findByName("moex_closed_warning").toString() + "\n")
                    else
                        Pair("closedWarning", ""),
                    Pair("kztBSell", qiwiKZT.second.buyingFor),     Pair("kztBBuy", qiwiKZT.second.sellingFor),
                    Pair("usdBSell", qiwiUSD.second.buyingFor),     Pair("usdBBuy", qiwiKZT.second.sellingFor),
                    Pair("eurBSell", qiwiEUR.second.buyingFor),     Pair("eurBBuy", qiwiEUR.second.sellingFor),
                    Pair("cnyBSell", qiwiCNY.second.buyingFor),     Pair("cnyBBuy", qiwiCNY.second.sellingFor),

                    Pair("time", Timestamper.getPrettyPrintedCurrentTime())
                )
            )
        }

        answer {
            addQueryResult {
                type = "article"
                additions = mapOf(
                    "title" to "Отправить текущий курс валют в чат",
                    "description" to "Источники: Мосбиржа, QIWI",
                    "input_message_content" to mapOf<String, Any>(
                        "message_text" to MessageSchema.findByName("current_exchange_rates")!!.applyParameters(*context.schemaFillers[0]),
                        "parse_mode" to "HTML"
                    )
                )
            }
        }
    }
}