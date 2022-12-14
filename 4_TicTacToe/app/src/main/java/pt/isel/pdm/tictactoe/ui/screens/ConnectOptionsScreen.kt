package pt.isel.pdm.tictactoe.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.model.Lobby
import pt.isel.pdm.tictactoe.ui.components.TopBar

@Composable
fun ConnectOptionsScreen(
    lobbies: List<Lobby>,
    createLobby: () -> Unit,
    joinLobby: (Lobby) -> Unit,
    showSettings: () -> Unit,

    ) {
    Scaffold(
        topBar = {
            TopBar(
                showSettings = showSettings
            )
        },
        floatingActionButton = {
            Button(
                onClick = { createLobby() },
                shape = CircleShape,
                modifier = Modifier
                    .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)

            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "",
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            for (lobby in lobbies) {
                Text(
                    text = lobby.displayName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .clickable {
                            joinLobby(lobby)
                        },
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}