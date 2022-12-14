package pt.isel.pdm.tictactoe.services

import kotlinx.coroutines.delay
import pt.isel.pdm.tictactoe.model.Lobby
import pt.isel.pdm.tictactoe.model.RemoteGame

class FakeRemoteService : RemoteGameService {

    override suspend fun getLobbies(): List<Lobby> {
        delay(1000)
        return listOf(
            DummyLobby("first"),
            DummyLobby("second"),
            DummyLobby("third"),
            DummyLobby("forth")
        )
    }

    override suspend fun create(name: String): Lobby {
        delay(1000)
        return DummyLobby("test")
    }

    override suspend fun waitForPlayer(l: Lobby) : String {
        delay(10000)
        throw Exception("cant wait for dummies")
    }

    override suspend fun join(l: Lobby, p: String): String {
        delay(2000)
        throw Exception("cant wait for dummies")
    }

    override suspend fun remove(l: Lobby) {
        delay(2000)
        // throw Exception("cant wait for dummies")
    }

    override suspend fun start(gameId: String): RemoteGame {
        TODO("Not yet implemented")
    }

    override suspend fun play(game: RemoteGame, moveIdx: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForPlay(game: RemoteGame): Int {
        TODO("Not yet implemented")
    }

    override suspend fun leaveGame(game: RemoteGame) {
        TODO("Not yet implemented")
    }

    override suspend fun onOtherPlayerStateChanged(): RemoteGame {
        TODO("Not yet implemented")
    }


    data class DummyLobby(override val displayName: String, override val gameId: String = "") : Lobby
}