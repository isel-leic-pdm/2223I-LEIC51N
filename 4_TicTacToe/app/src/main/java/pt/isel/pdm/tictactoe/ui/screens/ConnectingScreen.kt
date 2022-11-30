package pt.isel.pdm.tictactoe.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.ui.components.TopBar

@Composable
fun ConnectingScreen(
    backClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                backButtonClicked = { backClicked() },
            )
        }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                fontSize = 30.sp,
                text = stringResource(id = R.string.waiting_for_player),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)


            )
        }
    }
}