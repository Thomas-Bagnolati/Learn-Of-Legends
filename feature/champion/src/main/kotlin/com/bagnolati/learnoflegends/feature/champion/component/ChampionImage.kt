package com.bagnolati.learnoflegends.feature.champion.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.bagnolati.learnoflegends.feature.champion.ChampionUiState

@Composable
internal fun ChampionImage(
    modifier: Modifier = Modifier,
    championUiState: ChampionUiState.Success
) {
    val verticalGradientColors = listOf(
        MaterialTheme.colorScheme.background,
        Color.Transparent
    )
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = championUiState.champion.imageUrl.splash,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = verticalGradientColors
                    )
                )
        )
    }
}
