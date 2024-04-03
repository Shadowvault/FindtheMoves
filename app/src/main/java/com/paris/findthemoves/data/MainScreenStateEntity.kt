package com.paris.findthemoves.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paris.findthemoves.presentation.MainScreenState

@Entity
data class MainScreenStateEntity(
    @PrimaryKey val id: Int = 1,
    val redTile: Pair<Int, Int>,
    val greenTile: Pair<Int, Int>,
    val sliderValue: Float,
    val maxDepth: Int,
    val switchValue: Boolean,
    val chessboard: Array<Array<Long>>,
    val paths: List<List<Pair<Int, Int>>>
) {
    fun toMainScreenState(): MainScreenState {
        return MainScreenState(
            redTile = redTile,
            greenTile = greenTile,
            sliderValue = sliderValue,
            maxDepth = maxDepth,
            switchValue = switchValue,
            chessboard = chessboard,
            paths = paths
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MainScreenStateEntity

        if (id != other.id) return false
        if (redTile != other.redTile) return false
        if (greenTile != other.greenTile) return false
        if (sliderValue != other.sliderValue) return false
        if (maxDepth != other.maxDepth) return false
        if (switchValue != other.switchValue) return false
        if (!chessboard.contentDeepEquals(other.chessboard)) return false
        if (paths != other.paths) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + redTile.hashCode()
        result = 31 * result + greenTile.hashCode()
        result = 31 * result + sliderValue.hashCode()
        result = 31 * result + maxDepth
        result = 31 * result + switchValue.hashCode()
        result = 31 * result + chessboard.contentDeepHashCode()
        result = 31 * result + paths.hashCode()
        return result
    }
}