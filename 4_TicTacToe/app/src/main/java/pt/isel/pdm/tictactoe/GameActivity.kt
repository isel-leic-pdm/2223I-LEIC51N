package pt.isel.pdm.tictactoe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import pt.isel.pdm.tictactoe.helpers.viewModelInit
import pt.isel.pdm.tictactoe.model.GameState
import pt.isel.pdm.tictactoe.ui.screens.TicTacToeScreen
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme
import pt.isel.pdm.tictactoe.viewmodel.GameViewModel

class GameActivity : BaseActivity<GameViewModel>() {
    override val viewModel: GameViewModel by viewModels {
        viewModelInit {
            GameViewModel(
                dependencyContainer.gameService,
                dependencyContainer.userRepository,
                dependencyContainer.playedGamesRepository
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.startGame(navigationService.getGameIdExtra(this)!!)

        safeSetContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Box()
                    {
                        Column() {
                            TicTacToeScreen(
                                cells = viewModel.board,
                                cellClicked = { c -> viewModel.playOnCurrentCell(c) }
                            )
                        }

                        WaitForPlayerOverlay(canPlay = viewModel.canPlay)
                        GameEndOverlay(
                            playerWon = viewModel.playerWon,
                            state = viewModel.gameState
                        )

                    }

                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.leaveGame()
        finish()
    }
}

@Composable
fun WaitForPlayerOverlay(
    canPlay: Boolean
) {
    if (canPlay)
        return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.9f))
            .clickable { }

    ) {
        Text(
            text = stringResource(id = R.string.waiting_for_player),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun GameEndOverlay(
    playerWon: Boolean,
    state: GameState
) {
    if (state != GameState.DRAW && state != GameState.ENDED)
        return

    var color = if (playerWon) Color.Green else Color.Red

    if (state == GameState.DRAW)
        color = Color.Blue

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.copy(alpha = 0.9f))
            .clickable { }

    ) {
        val messageId = if (playerWon) R.string.player_won
        else if (state == GameState.DRAW)
            R.string.player_draw
        else
            R.string.player_lose

        Text(
            text = stringResource(id = messageId),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}