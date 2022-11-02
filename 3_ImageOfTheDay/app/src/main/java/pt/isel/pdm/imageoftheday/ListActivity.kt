package pt.isel.pdm.imageoftheday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.imageoftheday.ui.ListViewScreen
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme
import pt.isel.pdm.imageoftheday.viewmodel.ListViewModel
import pt.isel.pdm.imageoftheday.viewmodel.MainViewModel

class ListActivity : ComponentActivity() {

    private val nasaService by lazy { (application as DependencyContainer).imageService }
    private val navService by lazy { (application as DependencyContainer).navigationService }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<ListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListViewModel(nasaService) as T
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
                    ListViewScreen(
                        images = viewModel.imageList,
                        onElementClicked = { navService.navigateToDetail(this, it) },
                        onRefreshClicked = viewModel::refresh,
                        onBackClicked = { finish() },
                        isLoading = viewModel.isLoading
                    )
                }
            }
        }
    }
}
