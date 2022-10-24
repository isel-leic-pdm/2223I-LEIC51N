package pt.isel.pdm.imageoftheday.services

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import pt.isel.pdm.imageoftheday.ImageDetailActivity
import pt.isel.pdm.imageoftheday.InfoActivity
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.model.dto.LocalNasaImageDto
import pt.isel.pdm.imageoftheday.model.dto.NasaImage
import pt.isel.pdm.imageoftheday.model.dto.toLocalDto

class AppNavigationService : NavigationService {


    companion object {
        //        const val NASAIMAGE_DESCRIPTION = "NASAIMAGE_DESCRIPTION"
//        const val NASAIMAGE_TITLE = "NASAIMAGE_TITLE"
//        const val NASAIMAGE_AUTHOR = "NASAIMAGE_AUTHOR"
//        const val NASAIMAGE_URL = "NASAIMAGE_URL"
//        const val NASAIMAGE_DATE = "NASAIMAGE_DATE"
        const val DETAIL_EXTRA = "DETAIL_EXTRA"
    }

    override fun navigateToInfo(ctx: Activity) {
        navigateTo<InfoActivity>(ctx)
    }

//    override fun navigateToDetailold(ctx: Activity, image: NasaImage) {
//        //navigateTo<ImageDetailActivity>(ctx)
//
//        val intent = Intent(ctx, ImageDetailActivity::class.java)
//
//        intent.putExtra(NASAIMAGE_DESCRIPTION, image.description)
//        intent.putExtra(NASAIMAGE_TITLE, image.title)
//        intent.putExtra(NASAIMAGE_AUTHOR, image.author)
//        intent.putExtra(NASAIMAGE_URL, image.url)
//        intent.putExtra(NASAIMAGE_DATE, image.date)
//
//        ctx.startActivity(intent)
//    }

    override fun navigateToDetail(ctx: Activity, image: NasaImage) {
        navigateTo<ImageDetailActivity>(ctx, DETAIL_EXTRA, image.toLocalDto())
    }

    override fun getDetailData(t: Intent): NasaImage? {
        val data = t.getParcelableExtra<LocalNasaImageDto>(DETAIL_EXTRA);

        if (data == null)
            return null

        return NasaImage(data);
    }

    private inline fun <reified T> navigateTo(
        ctx: Activity,
        argumentName: String? = null,
        obj: Parcelable? = null
    ) {
        val intent = Intent(ctx, T::class.java)

        if (obj != null && argumentName != null)
            intent.putExtra(argumentName, obj)

        ctx.startActivity(intent)
    }

}