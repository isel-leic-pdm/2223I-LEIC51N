package pt.isel.pdm.imageoftheday.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.isel.pdm.imageoftheday.ui.SocialAccount
import pt.isel.pdm.imageoftheday.ui.theme.ImageOfTheDayTheme

@Composable
fun InfoScreen(
    backButton: () -> Unit,
    onEmailClicked: () -> Unit,
    socials: Iterable<SocialAccount>,
    onSocialClicked: (SocialAccount) -> Unit
) {
    ImageOfTheDayTheme() {
        Scaffold(
            topBar = { TopBar(backButtonClicked = { backButton() }) }) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Center)
                        .clickable { onEmailClicked() }

                ) {
                    Text(
                        text = "Made by Diogo Cardoso and PDM students",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "PDM 22/23I",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                }

                Row(modifier = Modifier.align(BottomCenter)) {
                    for (acc in socials)
                        SocialAccountView(acc, onSocialClicked)
                }
            }
        }
    }
}

@Composable
fun SocialAccountView(
    acc: SocialAccount,
    onClick: (SocialAccount) -> Unit
) {
    Box(
        modifier =
        Modifier
            .background(acc.color)
            .clickable { onClick(acc) })
    {
        Text(
            text = acc.social,
            modifier = Modifier.padding(12.dp),
            color = Color.White

        )
    }
}
