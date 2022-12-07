package pt.isel.pdm.tictactoe

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import pt.isel.pdm.tictactoe.ui.components.GenericErrorMessage
import pt.isel.pdm.tictactoe.viewmodel.ViewModelBase

abstract class BaseActivity<T> : ComponentActivity() where T : ViewModelBase {
    protected val dependencyContainer by lazy { (application as DependencyContainer) }
    protected val navigationService by lazy { dependencyContainer.navigationService }
    protected abstract val viewModel: T

    protected fun safeSetContent(content: @Composable () -> Unit) {
        setContent {
            content()
            GenericErrorMessage(state = viewModel.latestError)
        }
    }
}