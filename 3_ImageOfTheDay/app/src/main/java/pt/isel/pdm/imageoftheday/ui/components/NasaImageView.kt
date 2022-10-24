package pt.isel.pdm.imageoftheday.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.isel.pdm.imageoftheday.TAG
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.services.NasaImages
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme


@Composable
fun NasaImageView(
    image: NasaImage,
    onClick: ((NasaImage) -> Unit)? = null
) {

    Log.i(TAG, "NasaImageView Composition");
    ImageOfTheDayTheme() {
        val textGenericModifier = Modifier
            .padding(end = 6.dp)
            .fillMaxWidth()



        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable(
                    enabled = onClick != null,
                    onClick = { onClick?.invoke(image) }
                )
        )
        {
            Box(
                Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(Color.Green)
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
                        modifier = textGenericModifier,
                        textAlign = TextAlign.End
                    )
                }
            }

            Text(
                text = image.date,
                modifier = textGenericModifier,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.overline,

                )

            if (image.author != null)
                Text(
                    text = image.author,
                    modifier = textGenericModifier,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.subtitle1,
                    fontStyle = FontStyle.Italic

                )


        }
    }
}

