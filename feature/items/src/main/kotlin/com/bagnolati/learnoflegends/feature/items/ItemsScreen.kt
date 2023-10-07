package com.bagnolati.learnoflegends.feature.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bagnolati.learnoflegends.core.ui.preview.DevicesPreviews
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme

@Composable
internal fun ItemsRoute() {

    ItemsScreen()

}

@Composable
internal fun ItemsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Feature in progress...")
    }
}

@ThemePreviews
@DevicesPreviews
@Composable
private fun ItemScreenPreview() {
    LolTheme {
        Surface {
            ItemsScreen()
        }
    }
}