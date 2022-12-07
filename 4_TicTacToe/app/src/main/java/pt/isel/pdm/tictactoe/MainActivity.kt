package pt.isel.pdm.tictactoe

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import pt.isel.pdm.tictactoe.helpers.viewModelInit
import pt.isel.pdm.tictactoe.ui.screens.IntroScreen
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme
import pt.isel.pdm.tictactoe.viewmodel.SettingsViewModel

class MainActivity : BaseActivity<SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels {
        viewModelInit {
            SettingsViewModel(dependencyContainer.userRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.userDataExists()) {
            navigateToMenuAndFinish()
            return
        }

        safeSetContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    IntroScreen(
                        userName = viewModel.userName,
                        pieceSelected = viewModel.piece,
                        userNameChanged = {
                            viewModel.userName = it
                        },
                        onPieceSelected = {
                            viewModel.piece = it
                        },
                        setupCompleted = {
                            viewModel.saveUserData()
                            navigateToMenuAndFinish()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToMenuAndFinish() {
        //navigationService.navigateToMenu(this)
        navigationService.navigateToGame(this, "")
        finish()
    }
}