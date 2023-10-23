package com.bagnolati.learnoflegends.feature.champion

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.component.DynamicAsyncImage
import com.bagnolati.learnoflegends.core.ui.component.ErrorAlertDialog
import com.bagnolati.learnoflegends.core.ui.component.LoadingView
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.ChampionsPreviewParameterProvider
import com.bagnolati.learnoflegends.core.ui.preview.DevicesPreviews
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.core.ui.util.fromHtmlToAnnotatedString
import com.bagnolati.learnoflegends.feature.champion.component.PassiveItem
import com.bagnolati.learnoflegends.feature.champion.component.SpellItem
import com.bagnolati.learnoflegends.core.ui.R as uiR

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
        onClickRetryNetwork = viewModel::fetchChampion
    )
}

@Composable
internal fun ChampionScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    championUiState: ChampionUiState,
    onClickRetryNetwork: () -> Unit
) {
    val scrollState = rememberScrollState()
    val spacing = MaterialTheme.spacing

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        when (championUiState) {
            is ChampionUiState.Success -> {
                Column(
                    Modifier.verticalScroll(scrollState)
                ) {
                    DynamicAsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        imageUrl = championUiState.champion.skins[0].splashUrl,
                        contentScale = ContentScale.FillWidth,
                        placeholder = painterResource(id = uiR.drawable.ic_champion_placeholder_splash),
                        contentDescription = null
                    )
                    Presentation(
                        champion = championUiState.champion,
                        modifier = Modifier.padding(
                            horizontal = spacing.horizontalContent,
                            vertical = spacing.verticalContent
                        ),
                    )

                    Text(
                        text = stringResource(id = R.string.champion_passive),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(horizontal = spacing.horizontalContent)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                    championUiState.champion.passive?.let {
                        PassiveItem(
                            passive = it,
                            modifier = Modifier.padding(vertical = spacing.verticalContent, horizontal = spacing.horizontalContent),
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                    Text(
                        text = stringResource(id = R.string.champion_spells),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(horizontal = spacing.horizontalContent)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

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
                    onConfirmDialogError = onClickRetryNetwork
                )
            }
        }
    }

    ChampionTopAppBar(
        onClickNavigation = onBackClick,
    )

}

@Composable
private fun Presentation(
    modifier: Modifier = Modifier,
    champion: Champion
) {
    val spacing = MaterialTheme.spacing
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.medium),
    ) {
        Text(
            text = champion.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Text(
            text = champion.title.fromHtmlToAnnotatedString(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
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
                text = textTrunked.fromHtmlToAnnotatedString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { shouldExpand = !shouldExpand },
            )
        else
            Text(
                text = textComplete.fromHtmlToAnnotatedString(),
                style = MaterialTheme.typography.bodyMedium,
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
    onClickNavigation: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = onClickNavigation,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(0.4f),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = LolIcons.ArrowBack,
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
@DevicesPreviews
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
                onClickRetryNetwork = {}
            )
        }
    }
}