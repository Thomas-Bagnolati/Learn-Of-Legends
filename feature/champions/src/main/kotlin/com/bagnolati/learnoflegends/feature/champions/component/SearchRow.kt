package com.bagnolati.learnoflegends.feature.champions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing

@Composable
internal fun SearchRowSection(
    modifier: Modifier = Modifier,
    opened: Boolean,
    onClickSearchFab: () -> Unit,
    onSubmitKeyboard: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        if (opened) {
            // Focus text input when displayed
            LaunchedEffect(Unit) { focusRequester.requestFocus() }

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .focusRequester(focusRequester),
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                colors = TextFieldDefaults.colors(),
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { onSubmitKeyboard() })
            )
        }
        FloatingActionButton(
            modifier = Modifier.padding(
                bottom = MaterialTheme.spacing.floatingActionButton,
                end = MaterialTheme.spacing.floatingActionButton
            ),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            onClick = onClickSearchFab,
        ) {
            Icon(
                imageVector = if (opened) LolIcons.Close else LolIcons.Search,
                contentDescription = null
            )
        }

    }
}

@ThemePreviews
@Composable
private fun SearchRowPreview() {
    LolTheme {
        SearchRowSection(
            opened = true,
            searchQuery = "champion query",
            onClickSearchFab = {},
            onSearchQueryChange = {},
            onSubmitKeyboard = {}
        )
    }
}
