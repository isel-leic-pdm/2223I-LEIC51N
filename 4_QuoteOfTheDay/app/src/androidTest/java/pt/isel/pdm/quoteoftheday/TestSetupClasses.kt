package pt.isel.pdm.quoteoftheday

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

object TestQuotes {
    val default = Quote("quote", "author")
}

class TestQuoteService : QuoteService {
    override suspend fun fetchQuote(): Quote {
        return TestQuotes.default
    }
}

class TestQuoteOfTheDayApplication : Application(), DependencyContainer {
    override val quoteService by lazy { TestQuoteService() }

}

class QoDCustomRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestQuoteOfTheDayApplication::class.java.name, context)
    }


}
