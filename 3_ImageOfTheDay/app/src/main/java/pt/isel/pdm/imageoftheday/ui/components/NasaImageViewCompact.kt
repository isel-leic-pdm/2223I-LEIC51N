package pt.isel.pdm.imageoftheday.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.isel.pdm.imageoftheday.model.NasaImage

@Composable
fun NasaImageViewCompact(
    image: NasaImage,
    onClicked: (NasaImage) -> Unit
) {
    Box(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(Color.Green)
            .clickable { onClicked(image) }
    )
    {
        Image(
            //painter = painterResource(id = image.url),
            painter = rememberAsyncImagePainter(image.url),
            contentDescription = "",
            modifier = Modifier
                .height(400.dp)
                .align(Alignment.Center)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .background(Color(1f, 1f, 1f, 0.5f))
        ) {
            Text(

                text = image.title,
                textAlign = TextAlign.End
            )
        }
    }
}