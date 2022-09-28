package pt.isel.pdm.quoteoftheday

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pt.isel.pdm.quoteoftheday", appContext.packageName)
    }

    @get:Rule
    val testCompose = createComposeRule();

    @Test
    fun test_quoteview_shows_quote()
    {
        val quote = Quote("quote", "author")

        testCompose.setContent {
            QuoteView(q = quote)
        }

        testCompose.onNodeWithText(quote.quote).assertExists()
        testCompose.onNodeWithText(quote.author).assertExists()
    }

    @Test
    fun test_loadingbutton_changes_text_when_pressed()
    {

        val loading = mutableStateOf(false);
        testCompose.setContent {
            LoadingButton(
                isLoading = loading.value,
                onUpdateRequested =  {
                loading.value = !loading.value
            })
        }

        testCompose.onNode(isEnabled() and isDialog())
        val button = testCompose.onNodeWithTag(TestTags.LoadingButtonTag)


        button.assertIsEnabled()
        loading.value = true
        button.assertIsNotEnabled()

        loading.value = false
        button.performClick()

        button.assertIsNotEnabled()

        val resources = InstrumentationRegistry.getInstrumentation().getTargetContext().resources

        testCompose.onNodeWithText(resources.getString(R.string.loading)).assertExists()
        testCompose.onNodeWithText(resources.getString(R.string.fetch_quote)).assertDoesNotExist()

    }

}