package pt.isel.pdm.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pt.isel.pdm.tictactoe.model.UserData
import pt.isel.pdm.tictactoe.repository.UserRepository

class SettingsViewModel(
    val userRepo: UserRepository
) : ViewModelBase() {

    var piece: String by mutableStateOf("")
    var userName by mutableStateOf("")

    fun userDataExists(): Boolean {
        return userRepo.getUserData() != null

    }


    fun saveUserData() {
        userRepo.setUserData(UserData(userName, piece))
    }
}