package com.paris.findthemoves.presentation

import androidx.compose.ui.graphics.Color

data class MainScreenState(
    val redTile: Pair<Int, Int> = -1 to -1,
    val greenTile: Pair<Int, Int> = -1 to -1,
    val sliderValue: Float = 6f,
    val maxDepth: Int = 3,
    val switchValue: Boolean = true,
    val chessboard: Array<Array<Color>> = Array(6) { i ->
        Array(6) { j ->
            Color.Transparent
        }
    },
    val paths: List<List<Pair<Int, Int>>> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MainScreenState

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
        var result = redTile.hashCode()
        result = 31 * result + greenTile.hashCode()
        result = 31 * result + sliderValue.hashCode()
        result = 31 * result + maxDepth
        result = 31 * result + switchValue.hashCode()
        result = 31 * result + chessboard.contentDeepHashCode()
        result = 31 * result + paths.hashCode()
        return result
    }
}
