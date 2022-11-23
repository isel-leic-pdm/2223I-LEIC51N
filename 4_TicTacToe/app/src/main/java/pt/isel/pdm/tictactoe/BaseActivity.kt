package pt.isel.pdm.tictactoe

import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity() {
    protected val dependencyContainer by lazy { (application as DependencyContainer) }
    protected val navigationService by lazy { dependencyContainer.navigationService }
}