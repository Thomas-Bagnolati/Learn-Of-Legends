package com.bagnolati.learnoflegends.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.bagnolati.learnoflegends.feature.champion.navigation.championScreen
import com.bagnolati.learnoflegends.feature.champion.navigation.navigateToChampion
import com.bagnolati.learnoflegends.feature.champions.navigation.championsGraph
import com.bagnolati.learnoflegends.feature.champions.navigation.championsGraphRoutePattern
import com.bagnolati.learnoflegends.feature.items.navigation.itemsScreen
import com.bagnolati.learnoflegends.ui.LolAppState

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun LolNavHost(
    modifier: Modifier = Modifier,
    appState: LolAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    startDestination: String = championsGraphRoutePattern
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        itemsScreen()
        championsGraph(
            navigateToChampion = { championId ->
                navController.navigateToChampion(championId = championId)
            },
            nestedGraphs = {
                championScreen(
                    onBackClick = navController::popBackStack
                )
            }
        )
    }
}
