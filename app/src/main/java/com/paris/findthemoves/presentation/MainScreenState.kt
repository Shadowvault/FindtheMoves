package com.paris.findthemoves.presentation

import com.paris.findthemoves.presentation.utils.UIText

data class MainScreenState(
    val switchText: UIText = UIText.DynamicString(""),
    val buttonText: UIText = UIText.DynamicString(""),
    val resetButtonText: UIText = UIText.DynamicString(""),
    val foundPathsText: UIText = UIText.DynamicString(""),
    val redTile: Pair<Int, Int> = -1 to -1,
    val greenTile: Pair<Int, Int> = -1 to -1,
    val sliderValue: Float = 6f,
    val maxDepth: Int = 3,
    val switchValue: Boolean = true,
    val chessboard: Array<Array<Long>> = Array(6) { i ->
        Array(6) { j ->
            0x00000000
        }
    },
    val paths: List<List<Pair<Int, Int>>> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MainScreenState

        if (switchText != other.switchText) return false
        if (buttonText != other.buttonText) return false
        if (resetButtonText != other.resetButtonText) return false
        if (foundPathsText != other.foundPathsText) return false
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
        var result = switchText.hashCode()
        result = 31 * result + buttonText.hashCode()
        result = 31 * result + resetButtonText.hashCode()
        result = 31 * result + foundPathsText.hashCode()
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
