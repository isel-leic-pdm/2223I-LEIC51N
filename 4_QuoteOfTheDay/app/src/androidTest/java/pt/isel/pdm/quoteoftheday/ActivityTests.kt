package pt.isel.pdm.quoteoftheday

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityTests {

    @get:Rule
    val activityRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_fetch_quote_in_activity()
    {
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

        activityRule.onNodeWithTag(TestTags.QuoteViewTag).assertDoesNotExist()
        activityRule.onNodeWithText(resources.getString(R.string.no_quote)).assertExists()
        val button = activityRule.onNodeWithTag(TestTags.LoadingButtonTag)
        button.assertIsEnabled()

        button.performClick()

        activityRule.mainClock.advanceTimeBy(4000)
        activityRule.waitForIdle()

        activityRule.onNodeWithText(resources.getString(R.string.no_quote)).assertDoesNotExist()
        activityRule.onNodeWithTag(TestTags.QuoteViewTag).assertExists()


        activityRule.onNodeWithText(TestQuotes.default.quote).assertExists()
        activityRule.onNodeWithText(TestQuotes.default.author).assertExists()
    }

    @Test
    fun activity_maintains_state_after_rotation()
    {
        activityRule.onNodeWithTag(TestTags.LoadingButtonTag).performClick()

        activityRule.onNodeWithText(TestQuotes.default.quote).assertExists()
        activityRule.onNodeWithText(TestQuotes.default.author).assertExists()

        activityRule.activityRule.scenario.recreate()

        activityRule.onNodeWithText(TestQuotes.default.quote).assertExists()
        activityRule.onNodeWithText(TestQuotes.default.author).assertExists()
    }
}