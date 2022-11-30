package pt.isel.pdm.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pt.isel.pdm.tictactoe.model.Lobby
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.RemoteGameService

class ConnectViewModel(
    val remoteGameService: RemoteGameService,
    val userRepository: UserRepository
) : ViewModelBase() {

    var lobbies by mutableStateOf<List<Lobby>>(emptyList())
    var currentLobby by mutableStateOf<Lobby?>(null)

    private var _waitingJob: Job? = null

    fun loadLobbies() {
        viewModelScope.launch {
            lobbies = remoteGameService.getLobbies()
        }
    }

    fun createLobby() {
        _waitingJob = viewModelScope.launch {
            val userName = userRepository.getUserData()!!.userName;
            currentLobby = remoteGameService.create(userName)
            remoteGameService.waitForPlayer(currentLobby!!)
        }
    }

    fun joinLobby(lobby: Lobby) {
        _waitingJob = viewModelScope.launch {
            currentLobby = lobby
            remoteGameService.join(lobby)
        }
    }

    fun leaveLobby() {
        viewModelScope.launch {
            val lobby = currentLobby!!
            currentLobby = null
            _waitingJob?.cancel()
            remoteGameService.remove(lobby)
        }
    }


}