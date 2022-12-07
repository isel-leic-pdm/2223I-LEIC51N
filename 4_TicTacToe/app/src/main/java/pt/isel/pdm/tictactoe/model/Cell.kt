package pt.isel.pdm.tictactoe.model


data class Cell(
    val x: Int,
    val y: Int,
    val state: CellState
) {
}

object Cells {

    val emptyBoard: List<Cell> = List(9) {
        Cell(it % 3, it / 3, CellState.EMPTY)
    }

    val oCell = Cell(0, 0, CellState.O)

    val xCell = Cell(0, 0, CellState.X)


    fun checkPlayerWin(board: List<Cell>): Boolean {
        return checkRows(board) ||
                checkColumns(board) ||
                checkDiagonalLeftToRight(board) ||
                checkDiagonalRightToLeft(board)
    }

    private fun checkColumns(board: List<Cell>): Boolean {
        for (col in 0 until 3) {
            var count = 0
            for (row in 0 until 3) {
                count += board[col + row * 3].state.value
            }

            if (winConditionMet(count))
                return true
        }
        return false
    }


    private fun checkRows(board: List<Cell>): Boolean {
        for (row in 0 until 3) {
            var count = 0
            for (col in 0 until 3) {
                count += board[col + row * 3].state.value
            }

            if (winConditionMet(count))
                return true
        }
        return false
    }

    private fun checkDiagonalRightToLeft(board: List<Cell>): Boolean {

        /*
         *      /
         *     /
         *    /
         *   /
         * */
        var count = 0
        for (i in 0 until 3) {

            count += board[ (i * 3 + 2 - i) ].state.value

            if (winConditionMet(count))
                return true
        }
        return false
    }

    private fun checkDiagonalLeftToRight(board: List<Cell>): Boolean {

        /*
         * \
         *  \
         *   \
         *    \
         * */
        var count = 0
        for (i in 0 until 3) {

            count += board[i + i * 3].state.value

            if (winConditionMet(count))
                return true
        }
        return false
    }

    private fun winConditionMet(count: Int): Boolean {
        return count == CellState.O.value * 3 ||
                count == CellState.X.value * 3;
    }

}