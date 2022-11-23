package pt.isel.pdm.tictactoe.services

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import pt.isel.pdm.tictactoe.MenuActivity

class AppNavigationService(
) : NavigationService{


    private inline fun <reified T> navigateTo(
        context : Context,
        argumentName: String? = null,
        obj: Parcelable? = null
    ) {
        val intent = Intent(context, T::class.java)

        if (obj != null && argumentName != null)
            intent.putExtra(argumentName, obj)

        context.startActivity(intent)
    }

    override fun navigateToMenu(ctx: Context) {
        navigateTo<MenuActivity>(ctx);
    }
}