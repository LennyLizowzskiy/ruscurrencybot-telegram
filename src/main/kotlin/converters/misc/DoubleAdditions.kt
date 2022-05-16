package converters.misc

import kotlin.math.roundToInt

fun Double.cutToTwoDecimalPlaces(): Double = (this * 100.0).roundToInt() / 100.0