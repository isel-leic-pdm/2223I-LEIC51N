package pt.isel.pdm.imageoftheday.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.imageoftheday.ui.components.InfoScreen
import pt.isel.pdm.imageoftheday.ui.ui.theme.ImageOfTheDayTheme

class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfoScreen(
                backButton = { onBackButtonClicked() },
                onEmailClicked = { sendEmail() },
                socials = socialLinks,
                onSocialClicked = { acc -> onSocialClicked(acc) }
            )

        }
    }

    private fun onSocialClicked(acc: SocialAccount) {
        val intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse(acc.link)
        try {
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            Toast
                .makeText(
                    this,
                    e.message,
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }


    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("diogocardoso@imaginationoverflow.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello from PDM")
        try {
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            Toast
                .makeText(
                    this,
                    e.message,
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }

    private fun onBackButtonClicked() {
        finish()
    }
}


data class SocialAccount(
    val social: String,
    val link: String,
    val color: Color
)

private val socialLinks = listOf(
    SocialAccount(
        link = ("https://www.linkedin.com/in/diogocardoso89/"),
        social = "LinkedIn",
        color = Color(0, 119, 181)
    ),
    SocialAccount(
        link = ("https://www.facebook.com/diogocardoso89/"),
        social = "Facebook",
        color = Color(66, 103, 178)

    ),
    SocialAccount(
        link = ("https://twitter.com/dvd_pt/"),
        social = "Twitter",
        color = Color(29, 161, 242)
    ),
)
