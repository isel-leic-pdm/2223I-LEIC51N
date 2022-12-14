package pt.isel.pdm.imageoftheday.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pt.isel.pdm.imageoftheday.TAG

@Composable
fun NextPrevButtons(
    onPrev: (() -> Unit)?,
    onNext: (() -> Unit)?,
    disableButtons: Boolean
) {
    Row()
    {
        if (onPrev != null) {
            Button(
                onClick = {
                    Log.i(TAG, "Prev clicked ");
                    onPrev()
                },
                enabled = !disableButtons
            ) {
                Text("Prev")
            }
        }

        if (onNext != null) {
            Button(
                onClick = {
                    Log.i(TAG, "Next clicked ");
                    onNext()
                },
                enabled = !disableButtons
            ) {
                Text("Next")
            }
        }

    }
}