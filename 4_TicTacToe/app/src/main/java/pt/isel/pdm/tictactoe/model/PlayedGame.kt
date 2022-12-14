package pt.isel.pdm.tictactoe.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity()
data class PlayedGame(
    @PrimaryKey val playerName: String,
    var wins: Int,
    var loses: Int,
    var draws: Int
) {
    @Ignore
    val gamesPlayed = wins + loses + draws;
}
