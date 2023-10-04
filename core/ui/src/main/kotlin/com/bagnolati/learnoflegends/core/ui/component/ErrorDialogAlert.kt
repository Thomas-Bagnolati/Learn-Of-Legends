package com.bagnolati.learnoflegends.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme

/**
 * Alert dialog without dismiss request, is designed to be show when networks
 * call fails and need to success to continue.
 */
@Composable
fun ErrorAlertDialog(
    message: String?,
    onClickRetry: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Error", color = MaterialTheme.colorScheme.onErrorContainer) },
        icon = { Icon(imageVector = LolIcons.Warning, contentDescription = null) },
        onDismissRequest = {},
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) { Text(text = "Retry") }
        },
        text = {
            Text(
                text = message ?: "default error message",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        containerColor = MaterialTheme.colorScheme.errorContainer,
        textContentColor = MaterialTheme.colorScheme.onErrorContainer,
        titleContentColor = MaterialTheme.colorScheme.errorContainer,
        iconContentColor = MaterialTheme.colorScheme.onErrorContainer,
    )
}

@ThemePreviews
@Composable
private fun ErrorDialogAlertPreview() {
    LolTheme {
        Surface {
            ErrorAlertDialog(
                message = "Error occurred",
                onClickRetry = {}
            )
        }
    }
}