package com.bagnolati.learnoflegends.core.ui.util

/**
 * Calculate progress value for ProgressBar of the value compared to min and max values.
 *
 * @param value is the stat value.
 * @param minValue it the min value.
 * @param maxValue it the max value.
 *
 * @return progress value or null.
 */
fun calculateProgressValue(
    value: Double,
    minValue: Double,
    maxValue: Double
): Float? {
    return try {
        ((value - minValue) / (maxValue - minValue)).toFloat()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
