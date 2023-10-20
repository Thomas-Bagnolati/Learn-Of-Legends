package com.bagnolati.learnoflegends.feature.items.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.feature.items.ItemsSort

@Composable
fun SortItemRow(
    selected: Boolean = false,
    sort: ItemsSort,
    onClick: () -> Unit
) {
    val colorFilterIcon =
        if (sort.iconVisibleOnDark.not()) ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        else null

    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineContent = {
            Text(
                text = stringResource(id = sort.titleRes),
                color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        },
        leadingContent = {
            if (sort.icon != null)
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = sort.icon),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    colorFilter = colorFilterIcon
                )
            else
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = LolIcons.Filter,
                    contentDescription = null,
                )
        },
        colors = ListItemDefaults.colors(
            containerColor =
            if (selected) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface
        )
    )
}

@ThemePreviews
@Composable
private fun SortItemRowPreview() {
    LolTheme {
        Surface {
            SortItemRow(
                sort = ItemsSort.PERCENT_OMNIVAMP,
                onClick = {}
            )
        }
    }

}