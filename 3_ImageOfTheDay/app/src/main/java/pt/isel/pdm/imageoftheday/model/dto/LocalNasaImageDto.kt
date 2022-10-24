package pt.isel.pdm.imageoftheday.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pt.isel.pdm.imageoftheday.model.NasaImage

@Parcelize
data class LocalNasaImageDto(
    val title: String,
    val author: String?,
    val description: String,
    val date: String,
    val url: String,
) : Parcelable

fun NasaImage.toLocalDto(): LocalNasaImageDto {
    return LocalNasaImageDto(
        title,
        author,
        description,
        date,
        url
    )
}

fun NasaImage(dto: LocalNasaImageDto): NasaImage {
    return NasaImage(
        dto.title,
        dto.author,
        dto.description,
        dto.date,
        dto.url
    )
}
