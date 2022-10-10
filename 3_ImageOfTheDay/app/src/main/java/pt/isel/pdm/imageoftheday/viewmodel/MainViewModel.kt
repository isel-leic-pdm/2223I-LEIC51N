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
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(private val nasaService: NasaImageOfTheDayService) : ViewModel() {

    var nasaImage by mutableStateOf<NasaImage?>(null)

    var canTurnOnNext by mutableStateOf(false)

    var errorMessage: String? by mutableStateOf(null)

    private var currDate = LocalDate.now()
    private var todayDate = LocalDate.now()
    fun fetchTodayImage() {
        viewModelScope.launch {
            val dateString = currDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            try {
                nasaImage = nasaService.getImageOf(dateString)
            } catch (e: Exception) {
                errorMessage = e.message
            }
            canTurnOnNext = currDate < todayDate;
        }
    }

    fun fetchPrev() {
        currDate = currDate.minusDays(1)
        fetchTodayImage()
    }

    fun fetchNext() {
        currDate = currDate.plusDays(1)
        fetchTodayImage()
    }
}