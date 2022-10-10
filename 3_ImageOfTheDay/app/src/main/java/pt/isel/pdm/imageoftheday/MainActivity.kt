package pt.isel.pdm.imageoftheday


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.imageoftheday.ui.InfoActivity
import pt.isel.pdm.imageoftheday.ui.NasaImageScreen
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme
import pt.isel.pdm.imageoftheday.viewmodel.MainViewModel


const val TAG = "ImageOfTheDay"

class MainActivity : ComponentActivity() {

    private val nasaService by lazy { (application as DependencyContainer).imageService }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(nasaService) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageOfTheDayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var onNext: (() -> Unit)? = null

                    if (viewModel.canTurnOnNext)
                        onNext = { viewModel.fetchNext() }

                    NasaImageScreen(
                        viewModel.nasaImage,
                        fetchToday = { viewModel.fetchTodayImage() },
                        onPrev = viewModel::fetchPrev,
                        onNext = onNext,
                        navigateToInfoActivity = { navigateToInfoActivity() }
                    )

                    ErrorView(viewModel.errorMessage, { err ->
                        onError(err)
                        viewModel.errorMessage = null
                    })
                }
            }
        }
    }

    private fun onError(err: String) {
        Toast.makeText(
            this,
            err,
            Toast.LENGTH_LONG
        ).show()
    }

    fun navigateToInfoActivity() {
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }

}

@Composable
private fun ErrorView(
    errorMessage: String?,
    errorAvailable: (String) -> Unit
) {
    if (errorMessage == null)
        return

    errorAvailable(errorMessage)
}




