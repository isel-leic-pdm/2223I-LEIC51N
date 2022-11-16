package pt.isel.pdm.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    var userName by mutableStateOf("")

}