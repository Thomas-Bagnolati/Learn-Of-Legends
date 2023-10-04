package com.bagnolati.learnoflegends.feature.champions.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.preview.ChampionsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.core.ui.R as uiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampionRowItem(
    modifier: Modifier = Modifier,
    value: String,
    champion: Champion,
    selected: Boolean = false,
    onClick: (Champion) -> Unit,
) {
    val spacing = MaterialTheme.spacing
    Card(
        modifier = modifier,
        onClick = { onClick(champion) },
        shape = RoundedCornerShape(4.dp),
        elevation =
        if (selected) CardDefaults.elevatedCardElevation()
        else CardDefaults.cardElevation(),
        colors =
        if (selected) CardDefaults.cardColors(containerColor = colorScheme.primaryContainer)
        else CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChampionRowImage(imageUrl = champion.imageUrl.square)
            Spacer(modifier = Modifier.width(spacing.medium))
            Column {
                Text(text = champion.name, style = typography.titleMedium, color = colorScheme.onSurface)
                Text(text = champion.title, style = typography.bodySmall, color = colorScheme.primary)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = value, style = typography.titleSmall, color = colorScheme.onSurface,
                modifier = Modifier.padding(end = 19.dp)
            )
        }
    }
}

@Composable
private fun ChampionRowImage(imageUrl: String) {
    AsyncImage(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(2.dp)),
        model = imageUrl,
        placeholder = painterResource(uiR.drawable.ic_champion_placeholder),
        contentDescription = null
    )
}


@ThemePreviews
@Composable
private fun ChampionItemPreview(
    @PreviewParameter(ChampionsPreviewParameterProvider::class)
    champions: List<Champion>
) {
    LolTheme {
        ChampionRowItem(
            champion = champions.first(),
            onClick = {},
            value = "1.3",
        )
    }
}
