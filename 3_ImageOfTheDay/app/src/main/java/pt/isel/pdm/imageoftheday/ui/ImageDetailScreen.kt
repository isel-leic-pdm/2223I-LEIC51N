package pt.isel.pdm.imageoftheday.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.services.NasaImages
import pt.isel.pdm.imageoftheday.ui.components.NasaImageView
import pt.isel.pdm.imageoftheday.ui.components.TopBar
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme

@Composable
fun ImageDetailScreen(
    nasaImage: NasaImage,
    onBackClicked: () -> Unit
) {
    ImageOfTheDayTheme() {
        Scaffold(
            topBar = {
                TopBar(
                    backButtonClicked = onBackClicked,
                    title = { Text(text = nasaImage.title) }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                NasaImageView(image = nasaImage)
                Text(
                    text = nasaImage.description,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
        }
    }

}


@Preview
@Composable
fun TestImageDetailScreen() {
    ImageDetailScreen(
        nasaImage = NasaImages.Images[0],
        onBackClicked = {}
    )
}