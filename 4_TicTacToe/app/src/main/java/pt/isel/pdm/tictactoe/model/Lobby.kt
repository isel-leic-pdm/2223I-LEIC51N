package pt.isel.pdm.tictactoe.model

interface Lobby {
    val displayName: String
    val gameId: String
}

interface RemoteGame {
    val otherPlayerName: String
    val gameId: String
    val isMyTurn: Boolean
    val otherPlayerState: Int
}
