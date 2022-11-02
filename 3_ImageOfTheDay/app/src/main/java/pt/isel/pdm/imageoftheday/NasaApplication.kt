package pt.isel.pdm.imageoftheday

import android.app.Application
import pt.isel.pdm.imageoftheday.services.*

interface DependencyContainer {
    val imageService: NasaImageOfTheDayService
    val navigationService: NavigationService
}


class NasaApplication : Application(), DependencyContainer {
    override val imageService by lazy {
        RemoteNasaService("https://api.nasa.gov/planetary/apod?api_key=S6RMxbTyb9pAqhr823IBOzI3BNdtplUVxRRqw4z1")
        //FakeNasaService()
    }

    override val navigationService by lazy {
        AppNavigationService()
    }

}