package pt.isel.pdm.tictactoe.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.isel.pdm.tictactoe.model.PlayedGame

@Database(entities = [PlayedGame::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playedGames(): PlayedGameDao
}