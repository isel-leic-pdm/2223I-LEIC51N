package pt.isel.pdm.imageoftheday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.services.AppNavigationService
import pt.isel.pdm.imageoftheday.services.NasaImages
import pt.isel.pdm.imageoftheday.ui.ImageDetailScreen
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme

class ImageDetailActivity : ComponentActivity() {

    private val navService by lazy { (application as DependencyContainer).navigationService }

    override fun onCreate(savedInstanceState: Bundle?) {

        /*
        val nasaImage = NasaImage(
            description = intent.getStringExtra(AppNavigationService.NASAIMAGE_DESCRIPTION)!!,
            title = intent.getStringExtra(AppNavigationService.NASAIMAGE_TITLE)!!,
            author = intent.getStringExtra(AppNavigationService.NASAIMAGE_AUTHOR)!!,
            url = intent.getStringExtra(AppNavigationService.NASAIMAGE_URL)!!,
            date = intent.getStringExtra(AppNavigationService.NASAIMAGE_DATE)!!
        )
*/

        super.onCreate(savedInstanceState)
        val nasaImage = navService.getDetailData(intent)

        if (nasaImage == null)
        {
            finish()
            return
        }


        setContent {
            ImageOfTheDayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ImageDetailScreen(
                        nasaImage = nasaImage,
                        onBackClicked = { finish() },
                    )
                }
            }
        }
    }
}
