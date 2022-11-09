package pt.isel.pdm.imageoftheday

import android.app.Application
import androidx.work.*
import pt.isel.pdm.imageoftheday.services.*
import java.util.concurrent.TimeUnit

interface DependencyContainer {
    val imageService: NasaImageOfTheDayService
    val navigationService: NavigationService
}


class NasaApplication : Application(), DependencyContainer {
    override val imageService by lazy {
        RemoteNasaService(
            homeUrl = "https://api.nasa.gov/planetary/apod?api_key=S6RMxbTyb9pAqhr823IBOzI3BNdtplUVxRRqw4z1",
            cacheDir = cacheDir
        )
        //FakeNasaService()
    }

    override val navigationService by lazy {
        AppNavigationService()
    }

    override fun onCreate() {
        super.onCreate()


        val workRequest =
            PeriodicWorkRequestBuilder<ImageOfTheDayWorker>(repeatInterval = 12, TimeUnit.HOURS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "ImageWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )

    }

}