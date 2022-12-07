package pt.isel.pdm.tictactoe.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.model.Cell
import pt.isel.pdm.tictactoe.model.Cells
import pt.isel.pdm.tictactoe.ui.components.TicTacToeCell

@Preview
@Composable
fun TicTacToeScreenPreview() {
    TicTacToeScreen(cells = Cells.emptyBoard,{})
}

@Composable
fun TicTacToeScreen(
    cells: List<Cell>,
    cellClicked: (Cell) -> Unit
) {
    Box(
    ) {
        TicTacToeBoard(
            cells = cells,
            cellClicked = cellClicked
        )
    }
/*
    Box(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth(1 / 3f)
                .aspectRatio(1f)
                .background(Color.Blue)
                .align(Alignment.TopStart)
        )
        Box(
            Modifier
                .fillMaxWidth(1 / 3f)
                .aspectRatio(1f)
                .background(Color.Yellow)
                .align(Alignment.TopCenter)

        )
        Box(
            Modifier
                .fillMaxWidth(1 / 3f)
                .aspectRatio(1f)
                .background(Color.Red)
                .align(Alignment.TopEnd)

        )
    }
*/
}

@Composable
private fun TicTacToeBoard(
    cells: List<Cell>,
    cellClicked: (Cell) -> Unit
) {
    Box(
    )
    {
        Column {
            for (i in 0 until 3) {
                Row() {
                    for (j in 0 until 3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                        {
                            TicTacToeCell(
                                cell = cells[i * 3 + j],
                                clicked = cellClicked
                            )
                        }

                    }

                }

            }

        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .aspectRatio(1f),
            verticalArrangement = Arrangement.Center
        ) {

            Divider(
                color = MaterialTheme.colors.onSurface,
                thickness = 1.dp,
                modifier = Modifier.weight(1f, false)
            )
            Spacer(
                Modifier
                    .weight(1f)
            )

            Divider(
                color = MaterialTheme.colors.onSurface,
                thickness = 1.dp,
                modifier = Modifier.weight(1f, false)
            )


        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .aspectRatio(1f)
                .rotate(90f),
            verticalArrangement = Arrangement.Center


        ) {

            Divider(
                color = MaterialTheme.colors.onSurface,
                thickness = 1.dp,
                modifier = Modifier.weight(1f, false)
            )
            Spacer(
                Modifier
                    .weight(1f)
            )

            Divider(
                color = MaterialTheme.colors.onSurface,
                thickness = 1.dp,
                modifier = Modifier.weight(1f, false)
            )


        }

    }

}

@Preview
@Composable
fun ScreenPreview() {
    Row(Modifier.fillMaxSize()) {
        TicTacToeScreen(cells = Cells.emptyBoard,{})

    }
}


