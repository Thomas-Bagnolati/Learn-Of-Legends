package com.bagnolati.learnoflegends.feature.items.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.feature.items.ItemsSort
import com.bagnolati.nutrigood.core.domain.mapper.capitalize

@Composable
fun SortItemRow(
    selected: Boolean = false,
    sort: ItemsSort,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineContent = {
            Text(
                text = sort.name.capitalize()
                    .replace("_", " "),
                color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        },
        leadingContent = {
            Icon(
                LolIcons.Filter,
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

