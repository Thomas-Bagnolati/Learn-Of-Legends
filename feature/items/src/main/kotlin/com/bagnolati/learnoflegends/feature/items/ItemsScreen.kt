package com.bagnolati.learnoflegends.feature.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.core.ui.component.ErrorAlertDialog
import com.bagnolati.learnoflegends.core.ui.component.LoadingView
import com.bagnolati.learnoflegends.core.ui.component.SearchRowSection
import com.bagnolati.learnoflegends.core.ui.preview.DevicesPreviews
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.nutrigood.core.domain.mapper.capitalize
import com.bagnolati.learnoflegends.core.ui.R as uiR

@Composable
internal fun ItemsRoute(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel()
) {

    val itemsUiState by viewModel.itemsUiState.collectAsStateWithLifecycle()

    ItemsScreen(
        modifier = modifier,
        itemsUiState = itemsUiState,
        onClickRetry = viewModel::fetchItems,
        onSearchQueryChange = viewModel::onSearchQueryChange
    )

}

@Composable
internal fun ItemsScreen(
    modifier: Modifier = Modifier,
    itemsUiState: ItemsUiState,
    onSearchQueryChange: (String) -> Unit,
    onClickRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (itemsUiState) {
            is ItemsUiState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .imePadding(),
                    columns = GridCells.FixedSize(48.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    contentPadding = WindowInsets.safeGestures.asPaddingValues()
                ) {
                    categorySection(Item.Category.CONSUMABLE, itemsUiState.items)
                    categorySection(Item.Category.DISTRIBUTED, itemsUiState.items)
                    categorySection(Item.Category.TRINKET, itemsUiState.items)
                    categorySection(Item.Category.JUNGLE, itemsUiState.items)
                    categorySection(Item.Category.STARTER, itemsUiState.items)
                    categorySection(Item.Category.BOOT, itemsUiState.items)
                    categorySection(Item.Category.BASIC, itemsUiState.items)
                    categorySection(Item.Category.EPIC, itemsUiState.items)
                    categorySection(Item.Category.LEGENDARY, itemsUiState.items)
                    categorySection(Item.Category.MYTHIC, itemsUiState.items)
                    categorySection(Item.Category.ORNN, itemsUiState.items)
                    categorySection(Item.Category.EXCLUSIVE, itemsUiState.items)
                    categorySection(Item.Category.MINIONS_TURRET, itemsUiState.items)
                }

                var openSearchRow by remember { mutableStateOf(false) }
                SearchRowSection(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .imePadding(),
                    opened = openSearchRow,
                    searchQuery = itemsUiState.searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    onClickSearchFab = { openSearchRow = !openSearchRow },
                    onSubmitKeyboard = { /* TODO */ }
                )
            }

            is ItemsUiState.Error -> ErrorAlertDialog(
                message = itemsUiState.exception?.message,
                onClickRetry = onClickRetry
            )

            ItemsUiState.Loading -> LoadingView()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridScope.itemsList(items: List<Item>) {
    items(
        items = items,
        key = { it.id }
    ) {
        Card(
            Modifier.animateItemPlacement(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(6.dp)
        ) {
            Box(
                modifier = Modifier.size(48.dp),
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = it.image.full,
                    contentDescription = null,
                    placeholder = painterResource(id = uiR.drawable.ic_default_placeholder)
                )
                if (it.category == Item.Category.ORNN)
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.item_ornn_border),
                        contentDescription = null
                    )
            }
        }
    }
}

fun LazyGridScope.header(
    title: String,
) {
    item(span = { GridItemSpan(this.maxLineSpan) }) {
        Text(
            text = title, style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.small
            )
        )
    }
}

fun LazyGridScope.categorySection(
    category: Item.Category,
    items: List<Item>
) {
    val title = category.name.capitalize()
    val categoryItems = items.filter { it.category == category }
    if (categoryItems.isNotEmpty()) {
        header(title)
        itemsList(categoryItems)
    }
}

@ThemePreviews
@DevicesPreviews
@Composable
private fun ItemScreenPreview() {
    LolTheme {
        Surface {
            ItemsScreen(
                itemsUiState = ItemsUiState.Success(
                    items = listOf(),
                    searchQuery = ""
                ),
                onClickRetry = {},
                onSearchQueryChange = {}
            )
        }
    }
}