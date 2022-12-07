package pt.isel.pdm.tictactoe.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class ViewModelBase : ViewModel() {

    var latestError by mutableStateOf<ErrorModel?>(null)

    private var _errorId = 0

    protected fun safe(func: () -> Unit) {
        try {
            func()
        } catch (e: Exception) {
            latestError = ErrorModel(e.toString(), _errorId++)
        }
    }

    protected suspend fun safeSuspend(func: suspend () -> Unit) {
        try {
            func()
        } catch (e: Exception) {
            latestError = ErrorModel(e.toString(), _errorId++)
        }
    }

    /**
     * Calls func inside viewModelScope.launch, catching every exception.
     * If a CancellationException happens the function will ignore it
     * @return the Job from viewModelScope.launch
     */
    protected fun safeViewModelScopeLaunch(func: suspend () -> Unit): Job {
        return viewModelScope.launch {
            safeSuspend {
                try {

                    func()
                } catch (e: CancellationException) {
                    Log.d(this.javaClass.name, "Cancelled exception $e")
                }
            }
        }
    }



}

data class ErrorModel(val message: String, val id: Int)
