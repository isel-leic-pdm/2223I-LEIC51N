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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.isel.pdm.tictactoe.helpers.viewModelInit
import pt.isel.pdm.tictactoe.ui.screens.ConnectOptionsScreen
import pt.isel.pdm.tictactoe.ui.screens.ConnectingScreen
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme
import pt.isel.pdm.tictactoe.viewmodel.ConnectViewModel
import pt.isel.pdm.tictactoe.viewmodel.SettingsViewModel

class ConnectActivity : BaseActivity<ConnectViewModel>() {
    override val viewModel: ConnectViewModel by viewModels {
        viewModelInit {
            ConnectViewModel(
                dependencyContainer.gameService,
                dependencyContainer.userRepository
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        safeSetContent {

            LaunchedEffect(viewModel.gameId) {
                if (viewModel.gameId != null)
                    navigationService.navigateToGame(this@ConnectActivity, viewModel.gameId!!)

            }
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (viewModel.currentLobby == null)
                        ConnectOptionsScreen(
                            lobbies = viewModel.lobbies,
                            joinLobby = { viewModel.joinLobby(it) },
                            createLobby = { viewModel.createLobby() },
                            showSettings = { navigationService.navigateToSettings(this) }
                        )
                    else
                        ConnectingScreen(
                            backClicked = { viewModel.leaveLobby() }
                        )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.leaveLobby()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLobbies()
    }
    
    override fun onBackPressed() {
        if (!viewModel.isJoiningOrWaitingForPlayer)
            super.onBackPressed()
        else
            viewModel.leaveLobby()
    }


}