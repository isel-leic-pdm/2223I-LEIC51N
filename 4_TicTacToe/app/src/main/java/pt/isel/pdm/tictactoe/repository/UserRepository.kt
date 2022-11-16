package pt.isel.pdm.tictactoe.repository

import pt.isel.pdm.tictactoe.model.UserData

interface UserRepository {
    fun getUserData(): UserData?
    fun setUserData(usr: UserData?)
}