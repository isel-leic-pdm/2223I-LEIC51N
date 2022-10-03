package pt.isel.pdm.imageoftheday.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.services.NasaImageOfTheDayService
import java.time.LocalDate
import java.util.*

class MainViewModel(private val nasaService : NasaImageOfTheDayService) : ViewModel() {

    var nasaImage by mutableStateOf<NasaImage?>(null)

    var canTurnOnNext by mutableStateOf(false)


    fun fetchTodayImage()
    {
        viewModelScope.launch {
            nasaImage = nasaService.getTodayImage()

        }
    }

    fun fetchPrev()
    {
        viewModelScope.launch {
            val prevDate = ""
            nasaImage = nasaService.getImageOf(prevDate)
            canTurnOnNext = true
        }
    }

    fun fetchNext()
    {
        viewModelScope.launch {
            val nextDate = ""
            nasaImage = nasaService.getImageOf(nextDate)

            if(true ) //check if curr is current date
                canTurnOnNext = false
        }
    }
}