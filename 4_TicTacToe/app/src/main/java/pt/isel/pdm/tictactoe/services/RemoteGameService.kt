package pt.isel.pdm.tictactoe.services

import pt.isel.pdm.tictactoe.model.Lobby
import pt.isel.pdm.tictactoe.model.RemoteGame

interface RemoteGameService {
    suspend fun getLobbies(): List<Lobby>
    suspend fun create(name: String): Lobby
    suspend fun waitForPlayer(l: Lobby): String
    suspend fun join(l: Lobby, playerName: String): String
    suspend fun remove(l: Lobby)

    suspend fun start(gameId: String): RemoteGame
    suspend fun play(game: RemoteGame, moveIdx: Int)
    suspend fun waitForPlay(game: RemoteGame): Int
    suspend fun leaveGame(game: RemoteGame)

    suspend fun onOtherPlayerStateChanged(): RemoteGame
}

class GameEndedException(message: String) : Exception(message)