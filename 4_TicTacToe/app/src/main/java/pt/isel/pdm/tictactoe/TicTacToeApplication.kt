package pt.isel.pdm.tictactoe

import android.app.Application
import androidx.room.Room
import pt.isel.pdm.tictactoe.repository.AppDatabase
import pt.isel.pdm.tictactoe.repository.PlayedGamesRepository
import pt.isel.pdm.tictactoe.repository.PlayedGamesRoomRepository
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.*
import pt.isel.pdm.tictactoeprep.repositories.SharedPreferencesUserRepository

interface DependencyContainer {
    val navigationService: NavigationService
    val userRepository: UserRepository
    val playedGamesRepository: PlayedGamesRepository
    val gameService: RemoteGameService
    val appDatabase : AppDatabase
}

class TicTacToeApplication() : Application(), DependencyContainer {

    companion object
    {
        const val DB_NAME = "MY_DATABASE"
    }

    override val navigationService: NavigationService by lazy {
        AppNavigationService()
    }
    override val userRepository: UserRepository by lazy {
        SharedPreferencesUserRepository(this)
    }

    override val playedGamesRepository: PlayedGamesRepository by lazy {
        PlayedGamesRoomRepository(appDatabase)
    }
    override val gameService: RemoteGameService by lazy {
        //FakeRemoteService()
        FirestoreRemoteGameService(this)
    }
    override val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, DB_NAME
        ).build()
    }
}