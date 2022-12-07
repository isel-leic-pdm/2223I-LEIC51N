package pt.isel.pdm.tictactoe.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import pt.isel.pdm.tictactoe.model.Cell
import pt.isel.pdm.tictactoe.model.CellState
import pt.isel.pdm.tictactoe.model.Cells

@Composable
fun TicTacToeCell(
    cell: Cell,
    clicked: (Cell) -> Unit
) {

    //Log.d("CELL", "(${cell.x} ${cell.y}) = ${cell.state}")


    var modifier = Modifier.fillMaxSize();

    if (cell.state == CellState.EMPTY)
        modifier = modifier.then(Modifier.clickable {
            clicked(cell)
        })
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val value: String =
            when (cell.state) {
                //CellState.EMPTY -> Text(text = "${cell.x} ${cell.y} ${cell.state}")
                CellState.O -> "O"
                CellState.X -> "X"
                else -> ""
            }
        Text(
            text = value,
            overflow = TextOverflow.Ellipsis,
            fontSize = 100.sp,
            color = if (cell.state == CellState.O) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        )

    }
}

@Composable
@Preview
fun TicTacToeCellO() {
    TicTacToeCell(cell = Cells.oCell, {})
}

@Composable
@Preview
fun TicTacToeCellX() {
    TicTacToeCell(cell = Cells.xCell, {})
}

