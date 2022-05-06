package telegrambot.commands

import CurrencyUpdater.lastUpdateTime
import converters.MOEX
import converters.QIWI
import javascript.Timestamper
import models.telegrambot.messaging.commands.ChatCommand
import models.telegrambot.messaging.schemas.MessageSchema

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
        }

        reply {
            applyMessageSchema(
                MessageSchema.findByName("current_exchange_rates")!!
            )
        }
    }
}