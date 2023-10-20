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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.core.ui.util.fromHtmlToAnnotatedString


@Composable
fun PassiveItem(
    modifier: Modifier = Modifier,
    passive: Champion.Passive
) {
    val spacing = MaterialTheme.spacing
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 5.dp,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
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
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shadow(2.dp),
                    model = passive.imageUrl.passive,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Text(
                    text = passive.name.fromHtmlToAnnotatedString(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            Text(
                text = passive.description.fromHtmlToAnnotatedString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
