package pt.isel.pdm.tictactoe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.ui.components.PieceSelectorControl
import pt.isel.pdm.tictactoe.ui.components.TopBar
import pt.isel.pdm.tictactoe.ui.components.UserNameGetterControl

@Composable
fun SettingsScreen(
    userName: String = "",
    userNameChanged: ((String) -> Unit) = { },
    pieceSelected: String,
    onPieceSelected: (String) -> Unit,

    backClicked: () -> Unit,
    saveClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                backButtonClicked = { backClicked() },
            )
        },
        floatingActionButton = {
            Button(
                onClick = { saveClicked() },
                shape = CircleShape,
                modifier = Modifier
                    .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)

            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "",
                )
            }
        }) {
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            UserNameGetterControl(
                userName = userName,
                userNameChanged = userNameChanged,
                modifier = Modifier.align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(100.dp))
            PieceSelectorControl(
                pieceSelected = pieceSelected,
                onPieceSelected = onPieceSelected,
                modifier = Modifier.align(CenterHorizontally)
            )
        }
    }
}