package pt.isel.pdm.tictactoe

import android.app.Application
import pt.isel.pdm.tictactoe.repository.UserRepository
import pt.isel.pdm.tictactoe.services.AppNavigationService
import pt.isel.pdm.tictactoe.services.NavigationService
import pt.isel.pdm.tictactoeprep.repositories.SharedPreferencesUserRepository

interface DependencyContainer {
    val navigationService: NavigationService
    val userRepository: UserRepository
}

class TicTacToeApplication() : Application(), DependencyContainer {
    override val navigationService: NavigationService by lazy {
        AppNavigationService()
    }
    override val userRepository: UserRepository by lazy {
        SharedPreferencesUserRepository(this)
    }
}