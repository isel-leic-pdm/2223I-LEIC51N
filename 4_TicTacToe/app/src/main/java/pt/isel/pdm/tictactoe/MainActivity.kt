package pt.isel.pdm.tictactoe

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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.pdm.tictactoe.helpers.viewModelInit
import pt.isel.pdm.tictactoe.ui.screens.IntroScreen
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme
import pt.isel.pdm.tictactoe.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModels {
        viewModelInit {
            SettingsViewModel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    IntroScreen(
                        userName = viewModel.userName,
                        userNameChanged = {
                            viewModel.userName = it
                        })
                }
            }
        }
    }
}