package pt.isel.pdm.tictactoe.repository

import pt.isel.pdm.tictactoe.model.PlayedGame

class PlayedGamesRoomRepository(
    private val db: AppDatabase
) : PlayedGamesRepository {
    override suspend fun getAll(): List<PlayedGame> {
        return db.playedGames().getAll()
    }

    override suspend fun findByName(playerName: String): PlayedGame {
        val game = db.playedGames().findByName(playerName)

        if (game != null)
            return game

        db.playedGames().insertAll(PlayedGame(playerName, 0, 0, 0))

        return findByName(playerName)
    }

    override suspend fun update(game: PlayedGame) {
        db.playedGames().update(game)
    }

}