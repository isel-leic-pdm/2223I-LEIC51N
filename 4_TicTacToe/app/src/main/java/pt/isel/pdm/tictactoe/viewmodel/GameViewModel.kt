package pt.isel.pdm.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.pdm.tictactoe.model.*
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.RemoteGameService
import kotlin.random.Random

class GameViewModel(
    private val gameService: RemoteGameService,
    private val userRepository: UserRepository
) : ViewModelBase() {
    private lateinit var _remoteGame: RemoteGame
    val board: List<Cell>
        get() = _list

    var gameState: GameState by mutableStateOf(GameState.INVALID)
    var canPlay by mutableStateOf(false)
    //var canPlay: Boolean by mutableStateOf(false)

    private var _plays = 0
    private var _list = mutableStateListOf<Cell>()
    private var _currPlayer = CellState.EMPTY

    fun startGame(gameId: String) {

        _list.clear()

        //remote set play
        _list.addAll(Cells.emptyBoard)
        gameState = GameState.ONGOING
        _plays = 0

        safeViewModelScopeLaunch {
            _remoteGame = gameService.start(gameId)
            canPlay = _remoteGame.isMyTurn

            val favPlay = getUserFavCellState()

            if (canPlay)
                _currPlayer = favPlay
            else {
                _currPlayer = if (favPlay == CellState.O) CellState.X else CellState.O
                waitForPlayerAndNotifyItsMove()
            }
        }


    }

    private suspend fun waitForPlayerAndNotifyItsMove() {
        canPlay = false
        safeViewModelScopeLaunch {
            val playIndex = gameService.waitForPlay(_remoteGame)
            playOnCurrentCell(board[playIndex], isLocalMove = false)
            canPlay = true
        }
    }

    private fun getUserFavCellState(): CellState {
        val userData = userRepository.getUserData()
        if (userData?.favPlay == AppConstants.X)
            return CellState.X
        return CellState.O
    }

    fun playOnCurrentCell(c: Cell, isLocalMove: Boolean = true) {
        val moveIndex = c.y * 3 + c.x
        _list.set(moveIndex, Cell(c.x, c.y, _currPlayer))

        _plays++
        if (Cells.checkPlayerWin(board))
            gameState = GameState.ENDED
        else if (_plays == board.size)
            gameState = GameState.DRAW

        changePlayer();

        if (isLocalMove) {
            safeViewModelScopeLaunch {
                gameService.play(_remoteGame, moveIndex)
                waitForPlayerAndNotifyItsMove()
            }
        }
    }

    private fun changePlayer() {
        _currPlayer = if (_currPlayer == CellState.O) CellState.X else CellState.O
    }
}