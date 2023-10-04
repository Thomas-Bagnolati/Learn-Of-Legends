package com.bagnolati.learnoflegends.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.feature.champions.R as championsR
import com.bagnolati.learnoflegends.feature.items.R as itemsR

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composable.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    CHAMPIONS(
        selectedIcon = LolIcons.PersonFilled,
        unselectedIcon = LolIcons.Person,
        iconTextId = championsR.string.champions,
        titleTextId = championsR.string.champions
    ),
    ITEMS(
        selectedIcon = LolIcons.MenuBookFilled,
        unselectedIcon = LolIcons.MenuBook,
        iconTextId = itemsR.string.items,
        titleTextId = itemsR.string.items
    )
}
