package com.bagnolati.learnoflegends.feature.champions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.component.DynamicAsyncImage
import com.bagnolati.learnoflegends.core.ui.preview.ChampionsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.core.ui.util.asTextNumber
import com.bagnolati.learnoflegends.core.ui.util.bottomShadow
import com.bagnolati.learnoflegends.core.ui.util.calculateProgressValue
import com.bagnolati.learnoflegends.core.ui.R as uiR

@Composable
internal fun ChampionHeader(
    modifier: Modifier = Modifier,
    selectedChampion: Champion?,
    order: ChampionOrder,
    minStatValue: Double,
    maxStatValue: Double,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .bottomShadow(12.dp)
            .clickable { onClick() },
        onClick = onClick,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.surface)
    ) {
        ChampionBackground(
            modifier = Modifier.fillMaxSize(),
            selectedChampion = selectedChampion,
            contentPadding = PaddingValues(24.dp)
        ) {
            selectedChampion?.let { champion ->
                Row(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.small,
                        top = MaterialTheme.spacing.small
                    )
                ) {
                    ChampionImage(champion)
                    Column(
                        Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = champion.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = champion.title,
                            style = MaterialTheme.typography.labelMedium,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            champion.tags.forEach { tag ->
                                TagRow(tag)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(240.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(order.completeName),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = selectedChampion.getStatByOrder(order).asTextNumber(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    StatProgressBar(
                        progress = calculateProgressValue(
                            selectedChampion.getStatByOrder(order),
                            minStatValue,
                            maxStatValue
                        ) ?: 0f
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = minStatValue.asTextNumber(),
                            style = MaterialTheme.typography.labelMedium.copy(fontStyle = FontStyle.Italic),
                        )
                        Text(
                            text = maxStatValue.asTextNumber(),
                            style = MaterialTheme.typography.labelMedium.copy(fontStyle = FontStyle.Italic),
                        )

                    }
                }
            }

        }
    }
}

@Composable
private fun TagRow(tag: Champion.Tag) {
    DynamicAsyncImage(
        modifier = Modifier.size(16.dp),
        imageUrl = tag.tagImageUrl,
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
        text = tag.tagName,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun ChampionImage(champion: Champion) {
    val imageShape = MaterialTheme.shapes.extraLarge
    DynamicAsyncImage(
        modifier = Modifier
            .size(80.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                shape = imageShape
            )
            .clip(imageShape),
        imageUrl = champion.imageUrl.square,
        placeholder = painterResource(id = uiR.drawable.ic_champion_placeholder),
        contentDescription = null
    )
}

@Composable
private fun StatProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    LinearProgressIndicator(
        modifier = modifier
            .height(8.dp)
            .clip(CircleShape),
        progress = progress,
        strokeCap = StrokeCap.Round,
        trackColor = MaterialTheme.colorScheme.tertiaryContainer,
        color = MaterialTheme.colorScheme.tertiary
    )
}


@Composable
private fun ChampionBackground(
    modifier: Modifier = Modifier,
    selectedChampion: Champion?,
    contentPadding: PaddingValues,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Box(modifier) {
        DynamicAsyncImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = selectedChampion?.imageUrl?.splash,
            alignment = Alignment.TopCenter,
            alpha = 0.6f,
            contentScale = ContentScale.FillHeight,
            placeholder = painterResource(uiR.drawable.ic_champion_placeholder_transparent),
            placeholderColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(0.2f)),
            contentDescription = null
        )
        val brush = Brush.verticalGradient(
            listOf(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surface.copy(0.9f),
                MaterialTheme.colorScheme.surface.copy(0.5f),
            )
        )
        if (selectedChampion != null)
            Canvas(
                modifier = Modifier.fillMaxSize(),
                onDraw = {
                    drawRect(brush = brush)
                }
            )

        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}

@ThemePreviews
@Composable
private fun ChampionHeaderPreview(
    @PreviewParameter(ChampionsPreviewParameterProvider::class)
    champions: List<Champion>
) {
    val champion = champions.first()
    LolTheme {
        ChampionHeader(
            modifier = Modifier.height(200.dp),
            selectedChampion = champion,
            order = ChampionOrder.ARMOR,
            minStatValue = 50.0,
            maxStatValue = 200.0,
            onClick = {}
        )
    }
}
