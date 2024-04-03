package com.paris.findthemoves.presentation

sealed class MainScreenEvent {
    data class SliderValueChanged(val value: Float) : MainScreenEvent()
    data class MaxNumberOfMovesChanged(val moves: Int) : MainScreenEvent()
    data object SwitchValueChanged : MainScreenEvent()
    data class TilePressed(val pair: Pair<Int, Int>) : MainScreenEvent()
    data object ButtonPress : MainScreenEvent()
    data object ResetButtonPress : MainScreenEvent()
}