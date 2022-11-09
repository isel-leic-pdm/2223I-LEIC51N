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

class MainViewModel(private val nasaService: NasaImageOfTheDayService) : ViewModel() {

    var nasaImage by mutableStateOf<NasaImage?>(null)

    var canTurnOnNext by mutableStateOf(false)

    var errorMessage: String? by mutableStateOf(null)

    var isLoading by mutableStateOf(false)

    private var currDate = LocalDate.now()
    private var todayDate = LocalDate.now()

    fun fetchCurrentDateImage() {
        viewModelScope.launch {
            isLoading = true
            val dateString = currDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            try {
                nasaImage = nasaService.getImageOf(dateString, forceCache = true)
            } catch (e: Exception) {
                try {
                    nasaImage = nasaService.getImageOf(dateString, forceCache = false)
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            }

            isLoading = false
            canTurnOnNext = currDate < todayDate;
        }
    }


    fun fetchPrev() {
        currDate = currDate.minusDays(1)
        fetchCurrentDateImage()
    }

    fun fetchNext() {
        currDate = currDate.plusDays(1)
        fetchCurrentDateImage()
    }
}