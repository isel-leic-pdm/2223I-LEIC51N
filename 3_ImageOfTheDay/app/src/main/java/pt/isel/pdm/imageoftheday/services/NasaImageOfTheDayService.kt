package pt.isel.pdm.imageoftheday.services

import pt.isel.pdm.imageoftheday.model.NasaImage


interface NasaImageOfTheDayService {
    suspend fun getTodayImage() : NasaImage
    suspend fun getImageOf(date: String) : NasaImage
}