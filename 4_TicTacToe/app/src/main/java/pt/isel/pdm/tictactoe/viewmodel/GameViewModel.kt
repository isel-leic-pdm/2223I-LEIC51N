package pt.isel.pdm.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.pdm.tictactoe.model.*
import pt.isel.pdm.tictactoe.repository.PlayedGamesRepository
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.GameEndedException
import pt.isel.pdm.tictactoe.services.RemoteGameService
import kotlin.random.Random

class GameViewModel(
    private val gameService: RemoteGameService,
    private val userRepository: UserRepository,
    private val playedGamesRepository: PlayedGamesRepository
) : ViewModelBase() {

    private lateinit var _gameHistory: PlayedGame
    private lateinit var _remoteGame: RemoteGame
    val board: List<Cell>
        get() = _list

    var gameState: GameState by mutableStateOf(GameState.INVALID)
    var canPlay by mutableStateOf(false)
    var playerWon = false

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
            _gameHistory = playedGamesRepository.findByName(_remoteGame.otherPlayerName)
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
            try {
                val playIndex = gameService.waitForPlay(_remoteGame)
                playOnCurrentCell(board[playIndex], isLocalMove = false)
                canPlay = true

            } catch (e: GameEndedException) {
                handleGameEnding()
            }
        }
    }

    private fun handleGameEnding() {
        if (gameState == GameState.ONGOING) {
            playerWon = true
            gameState = GameState.ENDED
            _gameHistory.wins++
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
        if (Cells.checkPlayerWin(board)) {
            playerWon = isLocalMove
            gameState = GameState.ENDED
            if (playerWon)
                _gameHistory.wins++
            else
                _gameHistory.loses++

        } else if (_plays == board.size) {
            gameState = GameState.DRAW
            _gameHistory.draws++

        }

        changePlayer();

        if (isLocalMove) {
            safeViewModelScopeLaunch {
                try {
                    gameService.play(_remoteGame, moveIndex)
                } catch (e: GameEndedException) {
                    handleGameEnding()
                    return@safeViewModelScopeLaunch
                }

                waitForPlayerAndNotifyItsMove()
            }
        }
    }

    fun leaveGame() {
        safeViewModelScopeLaunch {
            //
            //  As is this can cause a race condition where the player that ended the game
            //  leaves the game before the other checks its move, the leave as is implemented
            //  will delete the document on firestore thus causing this issue
            //
            gameService.leaveGame(_remoteGame)
            playedGamesRepository.update(_gameHistory)
        }
    }

    private fun changePlayer() {
        _currPlayer = if (_currPlayer == CellState.O) CellState.X else CellState.O
    }


}