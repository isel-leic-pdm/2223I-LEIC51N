package pt.isel.pdm.imageoftheday.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import pt.isel.pdm.imageoftheday.R
import pt.isel.pdm.imageoftheday.model.NasaImage
import pt.isel.pdm.imageoftheday.model.dto.NasaImageDto
import java.io.File
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RemoteNasaService(
    private val homeUrl: String,
    private val cacheDir: File
) : NasaImageOfTheDayService {

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .cache(Cache(directory = cacheDir, maxSize = 50 * 1024 * 1024))
            .build()
    }

    private val gson = Gson()


    override suspend fun getImageOf(date: String, forceCache: Boolean): NasaImage {
        val url = homeUrl
            .toHttpUrlOrNull()!!
            .newBuilder()
            .addQueryParameter("date", date)
            .build()

        val requestBuilder = Request.Builder()
            .url(url)

        if (forceCache)
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE)

        val request = requestBuilder.build()

        return suspendCoroutine<NasaImage> { continuation ->
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {

                    if (response.isSuccessful == false || response.body == null)
                        continuation.resumeWithException(Exception(response.message))
                    else {
                        val contentData = response.body?.string()
                        val dto = gson.fromJson(contentData, NasaImageDto::class.java)
                        continuation.resume(NasaImageDto.toNasaImage(dto))
                    }
                }
            })
        }

    }

    override suspend fun getImages(count: Int): List<NasaImage> {
        val url = homeUrl
            .toHttpUrlOrNull()!!
            .newBuilder()
            .addQueryParameter("count", count.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        return suspendCoroutine { continuation ->
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {

                    if (response.isSuccessful == false || response.body == null)
                        continuation.resumeWithException(Exception(response.message))
                    else {
                        val contentData = response.body?.string()
                        val dto = gson.fromJson<List<NasaImageDto>>(
                            contentData,
                            object : TypeToken<ArrayList<NasaImageDto?>?>() {}.type
                        )
                        continuation.resume(dto.map { NasaImageDto.toNasaImage(it) })
                    }
                }
            })
        }

    }
}
