package com.bagnolati.learnoflegends.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * A class to model spacing values for LearnOfLegends.
 */
@Immutable
data class Spacing(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val horizontalContent: Dp = 19.dp,
    val verticalContent: Dp = 8.dp,
    val contentLazyColum: Dp = 14.dp,
    val contentLazyRow: Dp = 19.dp,
    val floatingActionButton: Dp = 18.dp,
)

/**
 * A composition local for [Spacing].
 */
val LocalSpacing = staticCompositionLocalOf { Spacing() }

/**
 * Extension to access spacing from [MaterialTheme]
 */
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current