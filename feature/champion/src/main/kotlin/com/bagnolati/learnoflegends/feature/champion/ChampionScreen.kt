package com.bagnolati.learnoflegends.feature.champion

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.component.ErrorAlertDialog
import com.bagnolati.learnoflegends.core.ui.component.LoadingView
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.ChampionsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.feature.champion.component.ChampionImage
import com.bagnolati.learnoflegends.feature.champion.component.PassiveItem
import com.bagnolati.learnoflegends.feature.champion.component.SpellItem

@Composable
internal fun ChampionRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChampionViewModel = hiltViewModel()
) {
    val championUiState by viewModel.championUiState.collectAsStateWithLifecycle()

    ChampionScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        championUiState = championUiState,
        onClickRetry = viewModel::fetchChampion
    )
}

@Composable
internal fun ChampionScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    championUiState: ChampionUiState,
    onClickRetry: () -> Unit
) {
    val scrollState = rememberScrollState()
    val spacing = MaterialTheme.spacing
    var imageHeight by remember { mutableFloatStateOf(1f) }
    var championImageIsShow by remember { mutableStateOf(true) }

    // Compare scrollState to champion imageSize to know if is show on the screen
    if (!isSystemInDarkTheme()) {
        LaunchedEffect(key1 = scrollState.value) {
            championImageIsShow = scrollState.value < imageHeight
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.surface
    ) {
        when (championUiState) {
            is ChampionUiState.Success -> {
                Column(
                    Modifier.verticalScroll(scrollState)
                ) {
                    ChampionImage(
                        championUiState = championUiState,
                        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                            imageHeight = layoutCoordinates.size.height.toFloat()
                        },
                    )
                    Presentation(
                        champion = championUiState.champion,
                        modifier = Modifier.padding(
                            horizontal = spacing.horizontalContent,
                            vertical = spacing.verticalContent
                        ),
                    )
                    Text(
                        text = "Passive",
                        color = colorScheme.onSurface,
                        style = typography.headlineSmall, modifier = Modifier
                            .padding(horizontal = spacing.horizontalContent)
                    )
                    PassiveItem(
                        // We know passive champion will never be nullable on this screen.
                        passive = championUiState.champion.passive!!,
                        modifier = Modifier.padding(vertical = spacing.verticalContent, horizontal = spacing.horizontalContent),
                    )
                    Text(
                        text = "Spells",
                        color = colorScheme.onSurface,
                        style = typography.headlineSmall, modifier = Modifier
                            .padding(horizontal = spacing.horizontalContent)
                    )
                    championUiState.champion.spells.forEach {
                        SpellItem(
                            spell = it,
                            modifier = Modifier.padding(vertical = spacing.verticalContent, horizontal = spacing.horizontalContent),
                        )
                    }

                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }

            is ChampionUiState.Loading -> LoadingView()

            // TODO : change to snackbar (we don't want to lock user screen).
            is ChampionUiState.Error -> {
                ErrorAlertDialog(
                    message = championUiState.error?.message,
                    onClickRetry = onClickRetry
                )
            }
        }
    }

    ChampionTopAppBar(
        onBackClick = onBackClick,
        isDarkBackground = championImageIsShow
    )

}

@Composable
private fun Presentation(
    modifier: Modifier = Modifier,
    champion: Champion
) {
    val spacing = MaterialTheme.spacing
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = champion.name,
            style = typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Text(
            text = champion.title,
            style = typography.labelLarge,
            color = colorScheme.secondary,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(spacing.large))

        ExpendableDescription(
            textTrunked = champion.blurbTrunked,
            textComplete = champion.blurbComplete
        )
    }
}


@Composable
private fun ExpendableDescription(
    textTrunked: String,
    textComplete: String
) {
    var shouldExpand by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.animateContentSize()
    ) {
        if (!shouldExpand)
            Text(
                text = textTrunked,
                style = typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { shouldExpand = !shouldExpand },
            )
        else
            Text(
                text = textComplete,
                style = typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { shouldExpand = !shouldExpand },
            )
        IconButton(
            modifier = Modifier.Companion.align(Alignment.CenterHorizontally),
            onClick = { shouldExpand = !shouldExpand }
        ) {
            Icon(
                imageVector =
                if (shouldExpand) LolIcons.ArrowDropUp
                else LolIcons.ArrowDropDown,
                contentDescription = null
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChampionTopAppBar(
    isDarkBackground: Boolean,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = LolIcons.ArrowBack,
                    tint = if (isDarkBackground) Color.White else colorScheme.onSurface,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@ThemePreviews
@Composable
private fun ChampionScreenPreview(
    @PreviewParameter(ChampionsPreviewParameterProvider::class)
    champions: List<Champion>
) {
    LolTheme {
        Surface {
            ChampionScreen(
                onBackClick = { },
                championUiState = ChampionUiState.Success(
                    champion = champions.first()
                ),
                onClickRetry = {}
            )
        }
    }
}