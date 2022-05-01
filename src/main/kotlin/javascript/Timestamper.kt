package javascript

import moment
import kotlin.js.Date

object Timestamper {
    fun currentTime() = Date.now()

    fun format(input: dynamic, format: String): dynamic = moment(input).format(format)

    fun getPrettyPrintedTime(input: dynamic): String = format(input, "HH:mm DD/MM/YYYY") as String
    fun getPrettyPrintedCurrentTime(): String = getPrettyPrintedTime(currentTime())
}

fun String.addTimestamp(time: dynamic, prefix: String = "\n"): String {
    return this + prefix + Timestamper.getPrettyPrintedTime(time)
}

fun String.addCurrentTimestamp(prefix: String = "\n"): String {
    return this + prefix + Timestamper.getPrettyPrintedCurrentTime()
}