package com.bagnolati.learnoflegends.feature.items.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bagnolati.learnoflegends.feature.items.ItemsRoute

const val itemsNavigationRoute = "items_route"

fun NavController.navigateToItems(navOptions: NavOptions? = null) {
    this.navigate(itemsNavigationRoute, navOptions)
}

fun NavGraphBuilder.itemsScreen() {
    composable(route = itemsNavigationRoute) {
        ItemsRoute()
    }
}
