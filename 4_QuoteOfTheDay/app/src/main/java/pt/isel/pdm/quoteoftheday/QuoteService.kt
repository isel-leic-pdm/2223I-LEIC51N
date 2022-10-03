package pt.isel.pdm.quoteoftheday

data class Quote(val quote:String, val author: String)

interface QuoteService {
    suspend fun fetchQuote() : Quote
}