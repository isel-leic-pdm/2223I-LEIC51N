package pt.isel.pdm.imageoftheday.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.services.NasaImageOfTheDayService

class ListViewModel(
    private val nasaService: NasaImageOfTheDayService
) : ViewModel() {
    var imageList by mutableStateOf<List<NasaImage>>(emptyList())
    var isLoading by mutableStateOf(false)


    companion object {
        const val NUMBER_IMAGES_FETCHED = 15
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading = true
            imageList = nasaService.getImages(NUMBER_IMAGES_FETCHED)
            isLoading = false
        }
    }
}