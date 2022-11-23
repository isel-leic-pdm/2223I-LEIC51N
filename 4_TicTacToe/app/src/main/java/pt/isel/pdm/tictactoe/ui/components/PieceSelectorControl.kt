package pt.isel.pdm.tictactoe.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.model.AppConstants

@Composable
fun PieceSelectorControl(
    pieceSelected: String,
    onPieceSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isXEnabled = remember {
        mutableStateOf(pieceSelected != AppConstants.X)
    }
    var isOEnabled = remember {
        mutableStateOf(pieceSelected != AppConstants.O)
    }
    Box(
        modifier = modifier
    )
    {
        Row() {
            PieceButton(
                piece = AppConstants.X,
                onPieceSelected = {
                    onPieceSelected(it)
                    isOEnabled.value = true
                    isXEnabled.value = false
                },
                isEnabled = isXEnabled.value
            )
            Spacer(modifier = Modifier.padding(12.dp))
            PieceButton(
                piece = AppConstants.O,
                onPieceSelected = {
                    onPieceSelected(it)
                    isOEnabled.value = false
                    isXEnabled.value = true
                },
                isEnabled = isOEnabled.value
            )
        }
    }

}

@Composable
fun PieceButton(
    piece: String,
    isEnabled: Boolean,
    onPieceSelected: (String) -> Unit,
) {
    Button(
        onClick = {
            onPieceSelected(piece)
        },
        enabled = isEnabled
    )
    {
        Text(
            text = piece,
            style = MaterialTheme.typography.h1
        )
    }
}