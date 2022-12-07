package pt.isel.pdm.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.helpers.viewModelInit
import pt.isel.pdm.tictactoe.ui.screens.TicTacToeScreen
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme
import pt.isel.pdm.tictactoe.viewmodel.ConnectViewModel
import pt.isel.pdm.tictactoe.viewmodel.GameViewModel

class GameActivity : BaseActivity<GameViewModel>() {
    override val viewModel: GameViewModel by viewModels {
        viewModelInit {
            GameViewModel(
                dependencyContainer.gameService
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.startGame(navigationService.getGameIdExtra(this)!!)

        setContent {
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

                            Text(text = viewModel.gameState.toString())
                            Button(onClick = {
                                viewModel.startGame("")
                            }) {

                                Text(text = "Reset")

                            }
                        }
                        if (viewModel.canPlay == false)
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Red.copy(alpha = 0.9f))
                                    .clickable { }

                            ) {
                                Text(
                                    text = stringResource(id = R.string.waiting_for_player),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                    }

                }
            }
        }
    }
}