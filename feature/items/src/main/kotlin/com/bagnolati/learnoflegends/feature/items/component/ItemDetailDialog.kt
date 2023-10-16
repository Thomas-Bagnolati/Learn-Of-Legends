package com.bagnolati.learnoflegends.feature.items.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.core.ui.component.DynamicAsyncImage
import com.bagnolati.learnoflegends.core.ui.preview.ItemsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.preview.manamunePreviewIndex
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.util.HtmlTags
import com.bagnolati.learnoflegends.core.ui.util.asTextNumber
import com.bagnolati.learnoflegends.core.ui.util.htmlToString
import com.bagnolati.learnoflegends.core.ui.util.toHtmlAnnotatedString
import com.bagnolati.learnoflegends.feature.items.ItemsSort
import com.bagnolati.learnoflegends.feature.items.getStatBySortItem
import com.bagnolati.learnoflegends.core.ui.R as uiR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailDialog(item: Item, onDismissRequest: () -> Unit) {
    AlertDialog(onDismissRequest = onDismissRequest) {
        Card(
            onClick = onDismissRequest,
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surface
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Header(item)
                Spacer(modifier = Modifier.height(18.dp))

                if (item.plaintext.isNotEmpty()) {
                    Text(
                        text = item.plaintext.htmlToString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                ItemsSort.values().forEach { sort ->
                    val stat = item.getStatBySortItem(sort)
                    val showStat = (stat > 0.0 && sort != ItemsSort.DEFAULT && sort != ItemsSort.GOLD)

                    if (showStat) {
                        Row(verticalAlignment = CenterVertically) {
                            if (sort.icon != null)
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = sort.icon),
                                    contentDescription = null
                                )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = stringResource(id = sort.titleRes), style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = stat.asTextNumber())
                        }
                    }

                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = item.description.toHtmlAnnotatedString(),
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

@Composable
private fun Header(item: Item) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DynamicAsyncImage(
            modifier = Modifier
                .size(74.dp)
                .clip(MaterialTheme.shapes.medium),
            imageUrl = item.image.full,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = item.name.htmlToString(),
                style = MaterialTheme.typography.titleLarge,
            )

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${item.gold.total} (${item.gold.sell})",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = uiR.drawable.gold_icon),
                    contentDescription = null
                )
            }

        }
    }
}

@ThemePreviews
@Composable
private fun ItemDetailDialogPreview(
    @PreviewParameter(ItemsPreviewParameterProvider::class)
    items: List<Item>
) {
    LolTheme {
        Surface(Modifier.fillMaxSize()) {
            ItemDetailDialog(
                item = items[manamunePreviewIndex],
                onDismissRequest = {}
            )
        }
    }
}