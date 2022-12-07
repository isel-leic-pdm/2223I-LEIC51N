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
    var imageList by mutableStateOf<Result<List<NasaImage>>?>(null)
    var isLoading by mutableStateOf(false)


    companion object {
        const val NUMBER_IMAGES_FETCHED = 15
    }

    fun abc(){}

    fun refresh() {
        var xpto : Int;
        viewModelScope.launch {abc()}
        viewModelScope.launch {
            xpto = 2;
            isLoading = true
            imageList = try {
                Result.success(nasaService.getImages(NUMBER_IMAGES_FETCHED))
            }
            catch (e:Exception)
            {
                Result.failure(e)
            }
            isLoading = false
        }
    }
}