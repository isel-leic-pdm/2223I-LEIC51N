package pt.isel.pdm.tictactoe.model

enum class CellState(val value: Int = 0) {
    EMPTY(0),
    X(1),
    O(20),
}

enum class GameState(val value : Int = 0)
{
    INVALID(0),
    ONGOING(1),
    DRAW(2),
    ENDED(3)
}