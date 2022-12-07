package pt.isel.pdm.tictactoe

import android.app.Application
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.*
import pt.isel.pdm.tictactoeprep.repositories.SharedPreferencesUserRepository

interface DependencyContainer {
    val navigationService: NavigationService
    val userRepository: UserRepository
    val gameService: RemoteGameService
}

class TicTacToeApplication() : Application(), DependencyContainer {
    override val navigationService: NavigationService by lazy {
        AppNavigationService()
    }
    override val userRepository: UserRepository by lazy {
        SharedPreferencesUserRepository(this)
    }
    override val gameService: RemoteGameService by lazy {
        //FakeRemoteService()
        FirestoreRemoteGameService(this)
    }
}