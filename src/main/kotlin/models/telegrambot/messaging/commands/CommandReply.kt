package models.telegrambot.messaging.commands

import models.telegrambot.messaging.context.Context

sealed class CommandReply(open val context: Context)