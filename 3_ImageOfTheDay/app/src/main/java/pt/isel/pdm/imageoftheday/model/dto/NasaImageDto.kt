package pt.isel.pdm.imageoftheday.model.dto

import com.google.gson.annotations.SerializedName
import pt.isel.pdm.imageoftheday.model.NasaImage

data class NasaImageDto(
    val copyright: String,
    @SerializedName("explanation")
    val description: String,
    val date: String,
    val url: String,
    val title: String
) {
    companion object {
        fun toNasaImage(remoteNasa: NasaImageDto): NasaImage {
            return NasaImage(
                title = remoteNasa.title,
                description = remoteNasa.description,
                date = remoteNasa.date,
                url = remoteNasa.url,
                author = remoteNasa.copyright
            );
        }
    }
}