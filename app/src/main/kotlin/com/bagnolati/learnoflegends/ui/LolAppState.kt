package com.bagnolati.learnoflegends.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bagnolati.learnoflegends.core.data.util.NetworkMonitor
import com.bagnolati.learnoflegends.feature.champions.navigation.championsRoute
import com.bagnolati.learnoflegends.feature.champions.navigation.navigateToChampionsGraph
import com.bagnolati.learnoflegends.feature.items.navigation.itemsNavigationRoute
import com.bagnolati.learnoflegends.feature.items.navigation.navigateToItems
import com.bagnolati.learnoflegends.navigation.TopLevelDestination
import com.bagnolati.learnoflegends.navigation.TopLevelDestination.CHAMPIONS
import com.bagnolati.learnoflegends.navigation.TopLevelDestination.ITEMS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@Composable
fun rememberLolAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
    navController: NavHostController = rememberNavController(),
): LolAppState {
    return remember(
        navController,
        windowSizeClass,
        coroutineScope,
        networkMonitor,
    ) {
        LolAppState(
            navController,
            windowSizeClass,
            coroutineScope,
            networkMonitor,
        )
    }
}

@Stable
class LolAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            championsRoute -> CHAMPIONS
            itemsNavigationRoute -> ITEMS
            else -> null
        }

    val shouldShowNavigation: Boolean
        @Composable get() = currentTopLevelDestination != null

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            CHAMPIONS -> navController.navigateToChampionsGraph(topLevelNavOptions)
            ITEMS -> navController.navigateToItems(topLevelNavOptions)
        }

    }
}
