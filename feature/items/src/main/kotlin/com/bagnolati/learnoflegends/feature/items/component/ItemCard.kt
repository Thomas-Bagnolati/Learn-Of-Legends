package com.bagnolati.learnoflegends.feature.items.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.core.ui.preview.ItemsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.util.asTextNumber
import com.bagnolati.learnoflegends.feature.items.R
import com.bagnolati.learnoflegends.core.ui.R as uiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    value: Double,
    item: Item,
    onClick: (Item) -> Unit,
    showValue: Boolean
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(6.dp),
        onClick = { onClick(item) },
    ) {
        Column {
            Box(
                modifier = Modifier.size(cellSize)
            ) {

                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = item.image.full,
                    contentDescription = null,
                    placeholder = painterResource(id = uiR.drawable.ic_default_placeholder),
                    contentScale = ContentScale.Crop
                )

                if (item.category == Item.Category.ORNN)
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.item_ornn_border),
                        contentDescription = null
                    )

            }
            if (showValue)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    text = value.asTextNumber(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
        }

    }
}

@ThemePreviews
@Composable
fun ItemCardPreview(
    @PreviewParameter(ItemsPreviewParameterProvider::class)
    items: List<Item>
) {
    val cellSize = 48.dp
    LolTheme {
        Surface(Modifier.width(cellSize)) {
            ItemCard(
                cellSize = cellSize,
                value = 2.0,
                item = items.first(),
                onClick = {},
                showValue = true
            )
        }
    }
}