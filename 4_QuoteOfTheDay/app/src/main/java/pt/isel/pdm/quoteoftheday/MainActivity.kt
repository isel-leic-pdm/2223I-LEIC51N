package pt.isel.pdm.quoteoftheday

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.quoteoftheday.ui.theme.QuoteOfTheDayTheme

const val TAG = "QuoteOfTheDay"

class MainActivity : ComponentActivity() {

    val quoteService: QuoteService by lazy { (application as DependencyContainer).quoteService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuoteOfTheDayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.d(TAG, "ONCREATE")
                    var quote by remember {
                        mutableStateOf<Quote?>(null)
                    }

                    var isLoading by remember {
                        mutableStateOf(false)
                    }

                    QuoteScreen(
                        quote = quote,
                        fetchQuote = {
                            isLoading = true;
                            quote = quoteService.fetchQuote()
                            isLoading = false;
                        },
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}

@Composable
fun QuoteScreen(quote: Quote?, isLoading: Boolean, fetchQuote: () -> Unit) {
    Log.d(TAG, "QuoteScreen")

    Column {
        if (quote == null)
            Text(text = stringResource(id = R.string.no_quote))
        else
            QuoteView(q = quote)

        LoadingButton(isLoading = isLoading, onUpdateRequested = fetchQuote)
    }
}

@Composable
fun LoadingButton(isLoading: Boolean, onUpdateRequested: () -> Unit) {
    Log.d(TAG, "LoadingButton")

    Button(
        onClick = onUpdateRequested,
        enabled = !isLoading,
        modifier = Modifier.testTag(TestTags.LoadingButtonTag)) {

        val text = if (isLoading)
            stringResource(id = R.string.loading)
        else
            stringResource(id = R.string.fetch_quote)

        Text(text = text)
    }
}

@Preview
@Composable
fun QuoteViewPreview() {
    QuoteView(q = Quote("cenas\ncoisas\naqui\nmuitogiras e tal", "Eu"))
}

@Composable
fun QuoteView(q: Quote) {
    Column(modifier = Modifier.testTag(TestTags.QuoteViewTag)) {
        Text(
            text = q.quote,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center
        )

        Text(
            text = q.author,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(End)
        )

    }
}