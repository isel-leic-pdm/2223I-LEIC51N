package pt.isel.pdm.tictactoeprep.repositories

import android.content.Context
import pt.isel.pdm.tictactoe.model.UserData
import pt.isel.pdm.tictactoe.repository.UserRepository

class SharedPreferencesUserRepository(
    private val context: Context
) : UserRepository {

    companion object {
        const val USER_NAME_PREF = "_userName"
        const val PREFERRED_PLAY_PREF = "_pplay"
        const val PREF_NAME = "UserRepository"
    }

    private val prefs by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


        override fun getUserData(): UserData? {
        val name = prefs.getString(USER_NAME_PREF, null)
        val play = prefs.getString(PREFERRED_PLAY_PREF, null)

        if (name == null || play == null)
            return null

        return UserData(name, play)

    }

    override fun setUserData(usr: UserData?) {
        val edit = prefs.edit()
        if (usr == null) {
            edit.remove(USER_NAME_PREF)
            edit.remove(PREFERRED_PLAY_PREF)
        } else {

            edit.putString(USER_NAME_PREF, usr.userName)
            edit.putString(PREFERRED_PLAY_PREF, usr.favPlay)
        }

        edit.apply()
    }
}