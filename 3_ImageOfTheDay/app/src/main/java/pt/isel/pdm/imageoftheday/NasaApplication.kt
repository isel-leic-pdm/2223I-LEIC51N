package pt.isel.pdm.imageoftheday

import android.app.Application
import pt.isel.pdm.imageoftheday.services.FakeNasaService
import pt.isel.pdm.imageoftheday.services.NasaImageOfTheDayService

interface DependencyContainer {
    val imageService: NasaImageOfTheDayService
}


class NasaApplication : Application(), DependencyContainer {
    override val imageService by lazy { FakeNasaService() }

}