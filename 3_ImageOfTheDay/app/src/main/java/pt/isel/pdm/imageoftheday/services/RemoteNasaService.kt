package pt.isel.pdm.imageoftheday.services

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import pt.isel.pdm.imageoftheday.R
import pt.isel.pdm.imageoftheday.model.NasaImage
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RemoteNasaService(
    private val homeUrl: String
) : NasaImageOfTheDayService {


    private val httpClient = OkHttpClient()


    override suspend fun getImageOf(date: String): NasaImage {
        val url = homeUrl
            .toHttpUrlOrNull()!!
            .newBuilder()
            .addQueryParameter("date", date)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        val resp = suspendCoroutine<Response> { continuation ->
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }
            })
        }

        val contentData = resp.body?.string()
        val X = contentData
        return NasaImage(X!!, ",", "", "", "", R.drawable.greatlacerta_ruuth_960)
    }
}