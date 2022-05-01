package models.telegrambot.messages.commands

import models.telegrambot.messages.context.Context

sealed class CommandReply(open val context: Context)