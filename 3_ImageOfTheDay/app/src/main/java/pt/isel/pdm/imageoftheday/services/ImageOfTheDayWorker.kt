package pt.isel.pdm.imageoftheday.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pt.isel.pdm.imageoftheday.DependencyContainer
import pt.isel.pdm.imageoftheday.NasaApplication
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ImageOfTheDayWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val myApp = applicationContext as NasaApplication
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        return try {
            myApp.imageService.getImageOf(today, forceCache = false)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}