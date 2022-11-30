package pt.isel.pdm.tictactoe.services

import kotlinx.coroutines.delay
import pt.isel.pdm.tictactoe.model.Lobby

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

    override suspend fun waitForPlayer(l: Lobby) {
        delay(10000)
        throw Exception("cant wait for dummies")
    }

    override suspend fun join(l: Lobby) {
        delay(2000)
        throw Exception("cant wait for dummies")
    }

    override suspend fun remove(l: Lobby) {
        delay(2000)
       // throw Exception("cant wait for dummies")
    }

    data class DummyLobby(override val displayName: String) : Lobby
}