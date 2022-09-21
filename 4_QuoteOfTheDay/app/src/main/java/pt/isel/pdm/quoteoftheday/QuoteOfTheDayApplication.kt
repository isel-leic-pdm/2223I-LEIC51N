package pt.isel.pdm.quoteoftheday

import android.app.Application
import java.lang.Thread.sleep

interface DependencyContainer {
    val quoteService: QuoteService
}

class FakeQuoteService : QuoteService {
    override fun fetchQuote(): Quote {
        val quoteText = "O poeta é um fingidor.\n" +
                "Finge tão completamente\n" +
                "Que chega a fingir que é dor\n" +
                "A dor que deveras sente."

        sleep(3000)
        return Quote(quoteText, "Fernando Pessoa")
    }
}

class QuoteOfTheDayApplication : DependencyContainer, Application() {
    override val quoteService: QuoteService by lazy { FakeQuoteService() }
}