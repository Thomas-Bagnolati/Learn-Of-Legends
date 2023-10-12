package com.bagnolati.learnoflegends.feature.champions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.component.ErrorAlertDialog
import com.bagnolati.learnoflegends.core.ui.component.LoadingView
import com.bagnolati.learnoflegends.core.ui.component.SearchRowSection
import com.bagnolati.learnoflegends.core.ui.preview.ChampionsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.DevicesPreviews
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.feature.champions.component.ChampionHeader
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrderColumn
import com.bagnolati.learnoflegends.feature.champions.component.ChampionRowItem
import com.bagnolati.learnoflegends.feature.champions.component.getStatByOrderAsString
import kotlinx.coroutines.delay
import java.util.*
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun ChampionsRoute(
    modifier: Modifier = Modifier,
    viewModel: ChampionsViewModel = hiltViewModel(),
    navigateToChampion: (String) -> Unit
) {
    val championUiState by viewModel.championsUiState.collectAsStateWithLifecycle()

    ChampionsScreen(
        modifier = modifier,
        championsUiState = championUiState,
        onClickChampionOrder = viewModel::orderChampions,
        onClickChampion = viewModel::selectChampion,
        onSearchQueryChange = viewModel::searchChampion,
        onClickErrorRefresh = viewModel::fetchChampions,
        onClickHeaderChampion = { it?.let { navigateToChampion(it.id) } },
        onSubmitKeyboard = viewModel::onSubmitKeyboard,
        onCloseSearchRow = viewModel::onCloseSearchRow
    )
}

@Composable
internal fun ChampionsScreen(
    modifier: Modifier = Modifier,
    championsUiState: ChampionsUiState,
    onClickChampionOrder: (order: ChampionOrder) -> Unit,
    onClickChampion: (Champion) -> Unit,
    onClickErrorRefresh: () -> Unit,
    onClickHeaderChampion: (Champion?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSubmitKeyboard: () -> Unit,
    onCloseSearchRow: () -> Unit
) {
    var expandFabOrder by remember { mutableStateOf(true) }
    var isSearchRowOpened by remember { mutableStateOf(false) }
    var isOrderMenuOpened by remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    val isScrollInProgress by remember {
        derivedStateOf { lazyListState.isScrollInProgress }
    }

    // Collapse floating action button on scroll
    LaunchedEffect(isScrollInProgress) {
        if (!isScrollInProgress) delay(3.seconds)
        expandFabOrder = !isScrollInProgress
    }

    Box(modifier.fillMaxSize()) {
        when (championsUiState) {
            is ChampionsUiState.Success -> {
                // Scroll on champion each times list of champions changes.
                LaunchedEffect(championsUiState.champions) {
                    championsUiState.indexOfSelectedChampion?.let {
                        lazyListState.scrollToItem(it)
                    }
                }

                Column {
                    ChampionHeader(
                        selectedChampion = championsUiState.selectedChampion,
                        order = championsUiState.selectedChampionOrder,
                        onClick = { onClickHeaderChampion(championsUiState.selectedChampion) },
                        minStatValue = championsUiState.minStatValue,
                        maxStatValue = championsUiState.maxStatValue
                    )
                    LazyColumnChampions(
                        modifier = Modifier,
                        champions = championsUiState.champions,
                        lazyState = lazyListState,
                        onClickChampion = onClickChampion,
                        indexOfSelectedChampion = championsUiState.indexOfSelectedChampion,
                        order = championsUiState.selectedChampionOrder,
                        contentPadding =
                        PaddingValues(
                            bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding(),
                            top = MaterialTheme.spacing.verticalContent
                        )
                    )
                }

                // FabSection
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .animateContentSize()
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End,
                ) {
                    if (!isOrderMenuOpened) {
                        SearchRowSection(
                            modifier = Modifier.imePadding(),
                            opened = isSearchRowOpened,
                            onClickSearchFab = {
                                isSearchRowOpened = !isSearchRowOpened
                                if (isSearchRowOpened.not()) onCloseSearchRow()
                            },
                            onSubmitKeyboard = {
                                onSubmitKeyboard()
                                isSearchRowOpened = !isSearchRowOpened
                            },
                            searchQuery = championsUiState.searchQuery,
                            onSearchQueryChange = onSearchQueryChange
                        )
                    }
                    AnimatedVisibility(!isSearchRowOpened) {
                        ChampionOrderColumn(
                            modifier = Modifier.fillMaxWidth(),
                            opened = isOrderMenuOpened,
                            selectedOrder = championsUiState.selectedChampionOrder,
                            expandFab = expandFabOrder,
                            onClickChampionOrder = onClickChampionOrder,
                            onClickFloatingActionButton = {
                                isOrderMenuOpened = !isOrderMenuOpened
                            }
                        )
                    }
                }
            }

            ChampionsUiState.Loading -> LoadingView()

            // TODO : change to snackbar (we don't want to lock user screen).
            is ChampionsUiState.Error -> ErrorAlertDialog(
                message = championsUiState.error?.message,
                onClickRetry = onClickErrorRefresh
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyColumnChampions(
    modifier: Modifier,
    champions: List<Champion>,
    lazyState: LazyListState,
    onClickChampion: (Champion) -> Unit,
    indexOfSelectedChampion: Int?,
    order: ChampionOrder,
    contentPadding: PaddingValues
) {
    val spacing = MaterialTheme.spacing
    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.horizontalContent),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = contentPadding
    ) {
        itemsIndexed(
            items = champions,
            key = { _, item -> item.id }
        ) { index, champion ->
            val selected = indexOfSelectedChampion == index
            ChampionRowItem(
                modifier = Modifier.animateItemPlacement(),
                value = champion.getStatByOrderAsString(
                    order = order,
                    indexOnList = index
                ),
                champion = champion,
                onClick = onClickChampion,
                selected = selected,
            )
        }
    }
}

@ThemePreviews
@DevicesPreviews
@Composable
private fun ChampionScreenPreview(
    @PreviewParameter(ChampionsPreviewParameterProvider::class)
    champions: List<Champion>
) {
    LolTheme {
        Surface {
            ChampionsScreen(
                championsUiState = ChampionsUiState.Success(
                    champions = champions,
                    selectedChampionOrder = ChampionOrder.ALPHABETIC,
                    selectedChampion = null,
                    indexOfSelectedChampion = null,
                    minStatValue = 20.0,
                    maxStatValue = 200.0,
                    searchQuery = ""
                ),
                onClickChampionOrder = {},
                onClickChampion = {},
                onSearchQueryChange = {},
                onClickErrorRefresh = {},
                onClickHeaderChampion = {},
                onSubmitKeyboard = {},
                onCloseSearchRow = {}
            )
        }
    }
}