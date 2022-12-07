package pt.isel.pdm.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.protobuf.Internal.BooleanList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.pdm.tictactoe.model.Lobby
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.RemoteGameService

class ConnectViewModel(
    private val remoteGameService: RemoteGameService,
    private val userRepository: UserRepository
) : ViewModelBase() {

    var lobbies by mutableStateOf<List<Lobby>>(emptyList())
    var currentLobby by mutableStateOf<Lobby?>(null)
    var gameId by mutableStateOf<String?>(null)

    val isJoiningOrWaitingForPlayer: Boolean
        get() = _waitingJob != null

    private var _waitingJob: Job? = null

    fun loadLobbies() {
        safeViewModelScopeLaunch {
            lobbies = remoteGameService.getLobbies()
        }
    }

    fun createLobby() {
        _waitingJob = safeViewModelScopeLaunch {
            val userName = userRepository.getUserData()!!.userName;
            currentLobby = remoteGameService.create(userName)
            gameId = remoteGameService.waitForPlayer(currentLobby!!)
        }
    }

    fun joinLobby(lobby: Lobby) {
        _waitingJob = safeViewModelScopeLaunch {
            currentLobby = lobby
            gameId = remoteGameService.join(lobby, userRepository.getUserData()!!.userName)
        }
    }

    fun leaveLobby() {
        if (_waitingJob == null) return

        safeViewModelScopeLaunch {
            val lobby = currentLobby!!
            currentLobby = null
            _waitingJob?.cancel()
            _waitingJob = null
            remoteGameService.remove(lobby)
        }

    }


}