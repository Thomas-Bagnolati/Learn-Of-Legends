package com.bagnolati.learnoflegends.core.ui.util

/**
 * Format Double to String as number
 */
fun Double.asTextNumber(): String {
    val asString = toString()
    return if (asString.endsWith(".0")) toInt().toString()
    else asString
}
