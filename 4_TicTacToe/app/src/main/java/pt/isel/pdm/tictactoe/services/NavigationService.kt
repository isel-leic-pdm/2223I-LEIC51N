package pt.isel.pdm.tictactoe.services

import android.app.Activity
import android.content.Context

interface NavigationService {
    fun navigateToMenu(ctx: Context)
    fun navigateToGame(ctx: Context, gameId: String)

    fun getGameIdExtra(ctx: Activity): String?

}