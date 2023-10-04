package com.bagnolati.learnoflegends.feature.champions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bagnolati.learnoflegends.feature.champions.ChampionsRoute

const val championsGraphRoutePattern = "champions_graph"
const val championsRoute = "champions_route"

fun NavController.navigateToChampionsGraph(navOptions: NavOptions? = null) {
    this.navigate(championsGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.championsGraph(
    navigateToChampion: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = championsGraphRoutePattern,
        startDestination = championsRoute
    ) {
        composable(route = championsRoute) {
            ChampionsRoute(
                navigateToChampion = navigateToChampion
            )
        }
        nestedGraphs()
    }
}