package com.bagnolati.learnoflegends.feature.champion.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bagnolati.learnoflegends.feature.champion.ChampionRoute
import java.net.URLDecoder


private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

const val championRoute = "champion_route"
internal const val championIdArg = "championId"

internal class ChampionArgs(val championId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(URLDecoder.decode(checkNotNull(savedStateHandle[championIdArg]), URL_CHARACTER_ENCODING))
}

fun NavController.navigateToChampion(championId: String) {
    val encodedId = Uri.encode(championId)
    this.navigate("$championRoute/$encodedId")
}

fun NavGraphBuilder.championScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$championRoute/{$championIdArg}",
        arguments = listOf(
            navArgument(championIdArg) { type = NavType.StringType }
        )
    ) {
        ChampionRoute(onBackClick = onBackClick)
    }
}