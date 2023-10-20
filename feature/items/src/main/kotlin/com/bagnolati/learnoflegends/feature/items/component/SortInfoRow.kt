package com.bagnolati.learnoflegends.feature.items.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.feature.items.ItemsSort

@Composable
internal fun SortInfoRow(
    sort: ItemsSort
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 5.dp
    ) {
        Row(
            Modifier.padding(
                vertical = 12.dp, horizontal = MaterialTheme.spacing.horizontalContent
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sort :", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(sort.titleRes),
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            if (sort.icon != null)
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = sort.icon),
                    contentDescription = null
                )
        }
    }
}

@Composable
@ThemePreviews
private fun SortInfoRow(

) {
    LolTheme {
        Surface {
            SortInfoRow(
                sort = ItemsSort.HEALTH
            )
        }
    }
}