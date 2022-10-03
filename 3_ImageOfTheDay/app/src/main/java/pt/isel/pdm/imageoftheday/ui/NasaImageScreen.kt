package pt.isel.pdm.imageoftheday.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import pt.isel.pdm.imageoftheday.R
import pt.isel.pdm.imageoftheday.TAG
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.ui.components.NasaImageView
import pt.isel.pdm.imageoftheday.ui.components.NextPrevButtons
import pt.isel.pdm.imageoftheday.ui.components.TopBar

@Composable
fun NasaImageScreen(
    image: NasaImage?,
    fetchToday: () -> Unit,
    onPrev: (() -> Unit)?,
    onNext: (() -> Unit)?,
) {
    Log.i(TAG, "NasaImageScreen Composition");

    Scaffold(
        topBar = { TopBar() },
    )
    { paddingValues ->
        Column() {
            Column() {
                if (image != null)
                    NasaImageView(image = image)
                else
                    Button(onClick = fetchToday) {
                        Text(text = stringResource(id = R.string.fetch_today))
                    }
            }

            if (image == null)
                return@Column

            NextPrevButtons(
                onNext = onNext,
                onPrev = onPrev
            )

        }
    }


}