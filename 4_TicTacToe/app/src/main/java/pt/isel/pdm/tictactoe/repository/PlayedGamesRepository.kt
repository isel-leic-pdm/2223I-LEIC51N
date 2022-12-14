package pt.isel.pdm.tictactoe.repository

import pt.isel.pdm.tictactoe.model.PlayedGame

interface PlayedGamesRepository {
    suspend fun getAll(): List<PlayedGame>

    suspend fun findByName(playerName: String): PlayedGame

    suspend fun update(game: PlayedGame)

}