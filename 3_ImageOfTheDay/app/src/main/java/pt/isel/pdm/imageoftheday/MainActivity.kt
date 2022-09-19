package pt.isel.pdm.imageoftheday


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme


const val TAG = "ImageOfTheDay"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageOfTheDayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NasaImageScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun NasaImageScreen() {

    Log.i(TAG,"NasaImageScreen Composition");

    var idx = remember{ mutableStateOf(0) }

    Column() {
        Column() {
            NasaImageView(image = NasaImages.Images[idx.value])
        }

        NextPrevButtons(
            onNext = { idx.value = (idx.value+1) % NasaImages.Images.size },
            onPrev = {
                if(idx.value == 0)
                    idx.value = NasaImages.Images.size - 1
                else
                    idx.value--;
            }
        )

    }
}


@Composable
fun NextPrevButtons(onPrev : ()->Unit, onNext : ()->Unit)
{
    Row()
    {
        Button(onClick = {
            Log.i(TAG,"Prev clicked ");
            onPrev()
        }) {
            Text("Prev")
        }
        Button(
            onClick = {
                Log.i(TAG,"Next clicked ");
                onNext()
            }) {
            Text("Next")
        }
    }
}

@Preview
@Composable
fun NasaImageViewPreview() {
    NasaImageView(image = NasaImages.Images[0])
}

@Composable
fun NasaImageView(image: NasaImage) {

    Log.i(TAG,"NasaImageView Composition");
    ImageOfTheDayTheme() {
        val textGenericModifier = Modifier
            .padding(end = 6.dp)
            .fillMaxWidth()



        Column(modifier = Modifier.padding(top = 12.dp))
        {
            Box(
                Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(id = image.resource),
                    contentDescription = "",
                    modifier = Modifier
                        .height(400.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillWidth
                )
                Box(
                    Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .align(Alignment.Center)
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

