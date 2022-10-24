package pt.isel.pdm.imageoftheday


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.imageoftheday.ui.NasaImageScreen
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme
import pt.isel.pdm.imageoftheday.viewmodel.MainViewModel


const val TAG = "ImageOfTheDay"

class MainActivity : ComponentActivity() {

    private val nasaService by lazy { (application as DependencyContainer).imageService }
    private val navService by lazy { (application as DependencyContainer).navigationService }


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
                        onNext = {
                            viewModel.fetchNext()
                        }

                    NasaImageScreen(
                        viewModel.nasaImage,
                        fetchToday = {
                            viewModel.fetchCurrentDateImage()
                        },
                        onPrev = viewModel::fetchPrev,
                        onNext = onNext,
                        navigateToInfoActivity = { navService.navigateToInfo(this) },
                        isLoading = viewModel.isLoading,
                        onImageClicked = { nasaImage -> navService.navigateToDetail(this, nasaImage) }
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




