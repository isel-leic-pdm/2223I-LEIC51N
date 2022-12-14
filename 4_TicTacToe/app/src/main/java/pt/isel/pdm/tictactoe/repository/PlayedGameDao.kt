package pt.isel.pdm.tictactoe.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pt.isel.pdm.tictactoe.model.PlayedGame

@Dao
interface PlayedGameDao {
    @Query("SELECT * FROM playedgame")
    suspend fun getAll(): List<PlayedGame>

    @Query("SELECT * FROM playedgame WHERE playerName LIKE :playerName LIMIT 1")
    suspend fun findByName(playerName: String): PlayedGame?

    @Insert
    suspend fun insertAll(vararg games: PlayedGame)

    @Delete
    suspend fun delete(game: PlayedGame)

    @Update
    suspend fun update(vararg games: PlayedGame)
}