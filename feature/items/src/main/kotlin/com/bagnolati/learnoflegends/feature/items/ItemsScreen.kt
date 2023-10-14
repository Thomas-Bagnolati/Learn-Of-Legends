package com.bagnolati.learnoflegends.feature.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.core.model.Item.Category
import com.bagnolati.learnoflegends.core.model.Item.Category.*
import com.bagnolati.learnoflegends.core.ui.component.ErrorAlertDialog
import com.bagnolati.learnoflegends.core.ui.component.LoadingView
import com.bagnolati.learnoflegends.core.ui.component.SearchRowSection
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.DevicesPreviews
import com.bagnolati.learnoflegends.core.ui.preview.ItemsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.feature.items.component.ItemCard
import com.bagnolati.learnoflegends.feature.items.component.SortItemRow
import com.bagnolati.nutrigood.core.domain.mapper.capitalize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun ItemsRoute(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel()
) {

    val itemsUiState by viewModel.itemsUiState.collectAsStateWithLifecycle()

    ItemsScreen(
        modifier = modifier,
        itemsUiState = itemsUiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onConfirmDialogError = viewModel::fetchItems,
        onSelectSort = viewModel::selectSort,
        onClickItem = { /*TODO*/ }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemsScreen(
    modifier: Modifier = Modifier,
    itemsUiState: ItemsUiState,
    onSearchQueryChange: (String) -> Unit,
    onConfirmDialogError: () -> Unit,
    onSelectSort: (ItemsSort) -> Unit,
    onClickItem: (Item) -> Unit
) {
    val density = LocalDensity.current

    var expandSortFab by remember { mutableStateOf(true) }
    var fabHeight by remember { mutableStateOf(0.dp) }

    var openSearchRow by remember { mutableStateOf(false) }
    var openModal by remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    val isScrollInProgress by remember {
        derivedStateOf { lazyListState.isScrollInProgress }
    }

    val categories = listOf<Category>(
        CONSUMABLE,
        DISTRIBUTED,
        TRINKET,
        JUNGLE,
        STARTER,
        BOOT,
        BASIC,
        EPIC,
        LEGENDARY,
        MYTHIC,
        ORNN,
        EXCLUSIVE,
        MINIONS_TURRET
    )


    // Collapse floating action button on scroll
    LaunchedEffect(isScrollInProgress) {
        if (!isScrollInProgress) delay(3.seconds)
        expandSortFab = !isScrollInProgress
    }

    Box(modifier.fillMaxSize()) {

        when (itemsUiState) {
            is ItemsUiState.Success -> {

                val cellSize = 48.dp

                Column {

                    AnimatedVisibility(
                        visible = itemsUiState.sort != ItemsSort.DEFAULT
                    ) {
                        SortInfo(itemsUiState)
                    }

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .padding(horizontal = MaterialTheme.spacing.horizontalContent),
                        columns = GridCells.FixedSize(cellSize),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        contentPadding =
                        PaddingValues(
                            bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
                                    // Add padding of floating action buttons height
                                    + if (!openSearchRow) fabHeight else 0.dp,
                            top = MaterialTheme.spacing.verticalContent,
                        )
                    ) {

                        categories.onEach { category ->

                            categorySection(
                                category = category,
                                itemsUiState = itemsUiState,
                                celleSize = cellSize,
                                onClickItem = onClickItem
                            )

                        }
                    }

                }

                // Fab section
                Column(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .onGloballyPositioned {
                            fabHeight = with(density) { it.size.height.toDp() }
                        },
                    horizontalAlignment = Alignment.End
                ) {

                    //Search
                    SearchRowSection(
                        modifier = Modifier.imePadding(),
                        opened = openSearchRow,
                        searchQuery = itemsUiState.searchQuery,
                        onSearchQueryChange = onSearchQueryChange,
                        onClick = {
                            openSearchRow = !openSearchRow
                            if (!openSearchRow) onSearchQueryChange("")
                        },
                        onSubmitKeyboard = {
                            openSearchRow = false
                            onSearchQueryChange("")
                            // TODO navigate to itemDetail
                        }
                    )

                    // Sort
                    AnimatedVisibility(visible = !openSearchRow) {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .padding(
                                    bottom = MaterialTheme.spacing.floatingActionButton,
                                    end = MaterialTheme.spacing.floatingActionButton
                                ),
                            text = {
                                Text(text = "Sort")
                            },
                            onClick = {
                                openModal = true
                            },
                            expanded = expandSortFab,
                            icon = {
                                Icon(imageVector = LolIcons.Filter, contentDescription = null)
                            },
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                }

                val itemsSort = ItemsSort.values()
                val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                val coroutineScope = rememberCoroutineScope()
                val sortsLazyState = rememberLazyListState()
                var rememberScrollPosition by remember {
                    mutableIntStateOf(sortsLazyState.firstVisibleItemIndex)
                }

                // remember position on scroll list
                LaunchedEffect(openModal) {
                    if (openModal) {
                        sortsLazyState.scrollToItem(rememberScrollPosition)
                    } else {
                        rememberScrollPosition = sortsLazyState.firstVisibleItemIndex
                    }
                }

                if (openModal)
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = { openModal = false }
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxHeight(0.5f),
                            state = sortsLazyState,
                            contentPadding = PaddingValues(
                                MaterialTheme.spacing.contentLazyColum
                            )
                        ) {
                            itemsIndexed(itemsSort) { index, sort ->
                                if (index != 0) Divider()

                                SortItemRow(
                                    selected = itemsUiState.sort == sort,
                                    sort = sort,
                                    onClick = {
                                        coroutineScope.launch {
                                            sheetState.hide()
                                            openModal = false
                                        }
                                        onSelectSort(sort)
                                    }
                                )
                            }
                        }
                    }
            }

            is ItemsUiState.Error -> ErrorAlertDialog(
                message = itemsUiState.exception?.message,
                onConfirmDialogError = onConfirmDialogError
            )

            ItemsUiState.Loading -> LoadingView()
        }
    }
}

@Composable
private fun SortInfo(itemsUiState: ItemsUiState.Success) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 5.dp
    ) {
        Row(
            Modifier.padding(
                vertical = 12.dp, horizontal = MaterialTheme.spacing.horizontalContent
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sort :", style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = itemsUiState.sort.name.capitalize().replace("_", " "),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridScope.itemsList(
    items: List<Item>,
    sort: ItemsSort,
    cellSize: Dp,
    onClickItem: (Item) -> Unit
) {
    items(
        items = items,
        key = { it.id }
    ) {
        val value = it.getStatBySortItem(sort)

        ItemCard(
            modifier = Modifier.animateItemPlacement(),
            cellSize = cellSize,
            value = value,
            item = it,
            onClick = onClickItem,
            showValue = sort != ItemsSort.DEFAULT
        )
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
    category: Category,
    itemsUiState: ItemsUiState.Success,
    celleSize: Dp,
    onClickItem: (Item) -> Unit
) {
    val title = category.name.capitalize()
    val categoryItems = itemsUiState.items.filter { it.category == category }

    if (categoryItems.isNotEmpty()) {

        header(title)
        itemsList(
            items = categoryItems,
            sort = itemsUiState.sort,
            cellSize = celleSize,
            onClickItem = onClickItem
        )

    }
}


@ThemePreviews
@DevicesPreviews
@Composable
private fun ItemScreenPreview(
    @PreviewParameter(ItemsPreviewParameterProvider::class)
    items: List<Item>
) {
    LolTheme {
        Surface {
            ItemsScreen(
                itemsUiState = ItemsUiState.Success(
                    items = items,
                    searchQuery = "",
                    sort = ItemsSort.FLAT_ARMOR
                ),
                onSearchQueryChange = {},
                onConfirmDialogError = {},
                onSelectSort = {},
                onClickItem = {}
            )
        }
    }
}