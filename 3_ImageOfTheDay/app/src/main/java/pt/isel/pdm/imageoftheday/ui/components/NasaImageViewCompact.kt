package pt.isel.pdm.imageoftheday.ui.components

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.isel.pdm.imageoftheday.model.NasaImage

@Composable
fun NasaImageViewCompact(
    image: NasaImage,
    onClicked: (NasaImage) -> Unit
) {
    Log.d("NasaImageViewCompact", "Drawing")
    var isExpanded = rememberSaveable { mutableStateOf(false) }
    NasaImageViewCompactStateless(
        image = image,
        onClicked = onClicked,
        isExpanded = isExpanded.value,
        toggleExpanded = {
            isExpanded.value = !isExpanded.value
        }
    )
}

@Composable
fun NasaImageViewCompactStateless(
    image: NasaImage,
    isExpanded: Boolean,
    toggleExpanded: () -> Unit,
    onClicked: (NasaImage) -> Unit
) {
    Log.d("NasaImageViewCompactStateless", "Drawing")

    //val targetHeight : Dp by animateDpAsState(targetValue = height)
    val height: Dp by animateDpAsState(
        targetValue = if (isExpanded) 400.dp else 100.dp,
        animationSpec = tween(
            delayMillis = 50,
            durationMillis = 800,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        Modifier
            .height(height)
            .fillMaxWidth()
            .background(Color.Green)
            .clickable { onClicked(image) }) {
        Image(
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

                text = image.title, textAlign = TextAlign.End
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 6.dp, end = 6.dp)
                .size(32.dp)
                .clickable { toggleExpanded() }
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .rotate(if (isExpanded) 180f else 0f)
                .background(MaterialTheme.colors.primary)
        )

    }
}