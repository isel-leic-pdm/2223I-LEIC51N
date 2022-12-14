package pt.isel.pdm.tictactoe.services

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import pt.isel.pdm.tictactoe.ConnectActivity
import pt.isel.pdm.tictactoe.GameActivity
import pt.isel.pdm.tictactoe.SettingsActivity

class AppNavigationService(
) : NavigationService {


    companion object {
        const val GAME_EXTRA = "GAME_EXTRA"
    }

    private inline fun <reified T> navigateTo(
        context: Context,
        argumentName: String? = null,
        obj: String? = null
    ) {
        val intent = Intent(context, T::class.java)

        if (obj != null && argumentName != null)
            intent.putExtra(argumentName, obj)

        context.startActivity(intent)
    }

    override fun navigateToMenu(ctx: Context) {
        navigateTo<ConnectActivity>(ctx);
    }

    override fun navigateToSettings(ctx: Context) {
        navigateTo<SettingsActivity>(ctx);
    }

    override fun navigateToGame(ctx: Context, gameId: String) {
        navigateTo<GameActivity>(ctx, GAME_EXTRA, gameId)
    }

    override fun getGameIdExtra(ctx: Activity): String? {
        return ctx.intent.getStringExtra(GAME_EXTRA)
    }
}