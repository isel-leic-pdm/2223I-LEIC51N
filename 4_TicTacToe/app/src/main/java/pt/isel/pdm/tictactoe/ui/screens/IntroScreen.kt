package pt.isel.pdm.tictactoe.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import pt.isel.pdm.tictactoe.ui.components.PieceSelectorControl
import pt.isel.pdm.tictactoe.ui.components.UserNameGetterControl
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun IntroScreen(
    userName: String,
    userNameChanged: (String) -> Unit,
) {

    var userNameFilled = remember {
        mutableStateOf(false)
    }
    TicTacToeTheme {
        Box(modifier = Modifier.fillMaxSize())
        {

            if (userNameFilled.value == false)
                UserNameGetterControl(
                    userName = userName,
                    userNameChanged = userNameChanged,
                    modifier = Modifier.align(Alignment.Center)
                )
            else
                PieceSelectorControl()

            Button(
                onClick = {
                    userNameFilled.value = true
                },
                shape = CircleShape,
                modifier = Modifier
                    .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)

            ) {

                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "",
                )


            }
        }
    }

}