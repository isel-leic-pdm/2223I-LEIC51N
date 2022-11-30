package pt.isel.pdm.tictactoe.services

import pt.isel.pdm.tictactoe.model.Lobby

interface RemoteGameService {
    suspend fun getLobbies(): List<Lobby>
    suspend fun create(name:String): Lobby
    suspend fun waitForPlayer(l: Lobby)
    suspend fun join(l: Lobby)
    suspend fun remove(l: Lobby)
}