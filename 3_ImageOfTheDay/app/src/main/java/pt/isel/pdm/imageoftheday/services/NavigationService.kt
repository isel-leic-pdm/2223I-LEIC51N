package pt.isel.pdm.imageoftheday.services

import android.app.Activity
import android.content.Intent
import pt.isel.pdm.imageoftheday.model.NasaImage

interface NavigationService {
    fun navigateToInfo(ctx: Activity)
    fun navigateToDetail(ctx: Activity, image:NasaImage)

    fun getDetailData(t: Intent) : NasaImage?
}