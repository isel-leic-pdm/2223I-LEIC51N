package pt.isel.pdm.quoteoftheday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteOfTheDayViewModel(
    private val quoteService: QuoteService
) : ViewModel() {
    var quote by mutableStateOf<Quote?>(null)
    var isLoading by mutableStateOf(false)

    fun fetchQuote() {

        viewModelScope.launch {
            isLoading = true
            quote = quoteService.fetchQuote()
            isLoading = false
        }

    }
}