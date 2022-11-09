package pt.isel.pdm.imageoftheday.services

import pt.isel.pdm.imageoftheday.model.NasaImage


interface NasaImageOfTheDayService {
    suspend fun getImageOf(date: String, forceCache: Boolean = false): NasaImage
    suspend fun getImages(count: Int): List<NasaImage>
}