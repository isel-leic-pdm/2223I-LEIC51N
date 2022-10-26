package pt.isel.pdm.imageoftheday.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.services.NasaImages
import pt.isel.pdm.imageoftheday.ui.components.NasaImageViewCompact
import pt.isel.pdm.imageoftheday.ui.components.TopBar
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme

@Composable
fun ListViewScreen(
    images: List<NasaImage>,
    onElementClicked: (NasaImage) -> Unit,
    onBackClicked: () -> Unit,
    onRefreshClicked: () -> Unit
) {
    ImageOfTheDayTheme() {
        Scaffold(topBar = {
            TopBar(
                backButtonClicked = onBackClicked,
            )
        }, floatingActionButton = {
            Button(
                onClick = onRefreshClicked,
                shape = CircleShape,
                modifier = Modifier.defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)

            ) {

                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Localized description",
                )

            }
        }) {
            Column() {
                for (img in images) {
                    NasaImageViewCompact(img, onElementClicked)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ListViewScreenPreview() {
    ListViewScreen(
        images = NasaImages.Images,
        onElementClicked = {},
        onBackClicked = {},
        onRefreshClicked = {}
    )
}