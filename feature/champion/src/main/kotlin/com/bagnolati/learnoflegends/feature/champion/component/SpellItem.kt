package com.bagnolati.learnoflegends.feature.champion.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.component.DynamicAsyncImage
import com.bagnolati.learnoflegends.core.ui.preview.ChampionsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing


@Composable
fun SpellItem(
    modifier: Modifier = Modifier,
    spell: Champion.Spell
) {
    val spacing = MaterialTheme.spacing
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 5.dp,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = spacing.horizontalContent,
                    vertical = spacing.medium
                ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                DynamicAsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shadow(2.dp),
                    imageUrl = spell.image.spell,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Text(
                    text = spell.name,
                    style = typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(spacing.small))
            Text(text = "Range : ${spell.rangeBurn}", style = typography.bodySmall)
            Text(text = "Cost : ${spell.costBurn}", style = typography.bodySmall)
            Text(text = "CoolDown : ${spell.coolDownBurn}", style = typography.bodySmall)
            Spacer(modifier = Modifier.height(spacing.medium))
            Text(
                text = spell.description,
                style = typography.bodyMedium
            )
        }
    }
}

@ThemePreviews
@Composable
private fun SpellItemPreview(
    @PreviewParameter(ChampionsPreviewParameterProvider::class)
    champions: List<Champion>
) {
    LolTheme {
        SpellItem(
            spell = champions.first().spells.first()
        )
    }
}