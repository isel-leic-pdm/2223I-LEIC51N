package pt.isel.pdm.imageoftheday.services

import pt.isel.pdm.imageoftheday.model.NasaImage


interface NasaImageOfTheDayService {
    suspend fun getImageOf(date: String): NasaImage
    suspend fun getImages(count: Int): List<NasaImage>
}