package pt.isel.pdm.tictactoe.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.viewmodel.ErrorModel


@Composable
fun GenericErrorMessage(
    state: ErrorModel?,
    onDismiss: () -> Unit = { },
) {
    val lastError: MutableState<ErrorModel?> =
        remember { mutableStateOf(null) }

    if (state == null || lastError.value == state)
        return

    ErrorAlertImpl(
        title = stringResource(id = R.string.error),
        message = state.message,
        buttonText = stringResource(id = R.string.error_button),
        onDismiss = {
            onDismiss()
            lastError.value = state
        },
        onButton = {
            onDismiss()
            lastError.value = state
        }
    )
}

@Composable
private fun ErrorAlertImpl(
    title: String,
    message: String,
    buttonText: String,
    onButton: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                OutlinedButton(
                    border = BorderStroke(0.dp, Color.Unspecified),
                    onClick = onButton
                ) {
                    Text(text = buttonText)
                }
            }
        },
        title = { Text(text = title) },
        text = { Text(text = message) },
        modifier = Modifier.testTag("ErrorAlert")
    )
}
