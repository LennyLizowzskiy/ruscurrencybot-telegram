package telegrambot.commands

import CurrencyUpdater
import converters.MOEX
import converters.QIWI
import javascript.Timestamper
import models.telegrambot.messages.commands.ChatCommand
import models.telegrambot.messages.schemas.MessageSchema

fun registerChatCommands(): Unit = with(ChatCommand) {
    register("start") {
        reply {
            text = "<b>Команды:</b>\n\n/currency - Получить текущий курс валют в ЦБ РФ и QIWI"
        }
    }

    register("sources") {
        reply {
            text = "<b>Исходный код данного бота:</b>\n\nhttps://git.lizowzskiy.su/lizowzskiy/ruscurrencybot-telegram"
        }
    }

    register("currency") {
        executeBeforeReply {
            val qiwiKZT = QIWI.getCurrencyByCharCode("KZT")
            val qiwiUSD = QIWI.getCurrencyByCharCode("USD")
            val qiwiEUR = QIWI.getCurrencyByCharCode("EUR")
            val qiwiCNY = QIWI.getCurrencyByCharCode("CNY")

            context.schemaFillers = arrayOf(
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
        }

        reply {
            applyMessageSchema(
                MessageSchema.findByName("current_exchange_rates")!!
            )
        }
    }
}