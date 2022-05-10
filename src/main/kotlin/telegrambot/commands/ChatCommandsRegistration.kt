package telegrambot.commands

import converters.AliExpress
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
        }

        reply {
            applyMessageSchema(
                MessageSchema.findByName("current_exchange_rates")!!
            )
        }
    }
}