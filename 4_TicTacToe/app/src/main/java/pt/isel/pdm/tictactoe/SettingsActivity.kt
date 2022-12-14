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
import pt.isel.pdm.tictactoe.helpers.viewModelInit
import pt.isel.pdm.tictactoe.ui.screens.SettingsScreen
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme
import pt.isel.pdm.tictactoe.viewmodel.SettingsViewModel

class SettingsActivity : BaseActivity<SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels {
        viewModelInit {
            SettingsViewModel(dependencyContainer.userRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUserData()
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SettingsScreen(
                        userName = viewModel.userName,
                        userNameChanged = { viewModel.userName = it },
                        pieceSelected = viewModel.piece,
                        onPieceSelected = { viewModel.piece = it },
                        backClicked = { finish() },
                        saveClicked = { viewModel.saveUserData() }
                    )
                }
            }
        }
    }
}

