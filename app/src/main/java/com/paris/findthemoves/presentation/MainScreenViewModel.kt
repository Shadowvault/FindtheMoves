package com.paris.findthemoves.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paris.findthemoves.data.ScreenRepository
import com.paris.findthemoves.domain.usecases.findpaths.KnightPathsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val knightPathsUseCase: KnightPathsUseCase,
    private val mainScreenStateRepository: ScreenRepository
) :
    ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState = _mainScreenState.asStateFlow()

    init {
        viewModelScope.launch {

            val restoredState = mainScreenStateRepository.getScreenStateById(1)

            restoredState?.let {
                _mainScreenState.value = _mainScreenState.value.copy(
                    redTile = it.redTile,
                    greenTile = it.greenTile,
                    chessboard = it.chessboard,
                    paths = it.paths
                )
            } ?: run {
                val size = _mainScreenState.value.sliderValue.toInt()
                val newChessboard = Array(size) { i ->
                    Array(size) { j ->
                        startingTileColor(i, j)
                    }
                }
                _mainScreenState.value = _mainScreenState.value.copy(chessboard = newChessboard)
            }
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.SliderValueChanged -> {
                viewModelScope.launch {
                    _mainScreenState.value = _mainScreenState.value.copy(
                        greenTile = -1 to -1,
                        redTile = -1 to -1,
                        sliderValue = event.value,
                        chessboard = Array(event.value.toInt()) { i ->
                            Array(event.value.toInt()) { j ->
                                startingTileColor(i, j)
                            }
                        }
                    )
                }
            }

            is MainScreenEvent.SwitchValueChanged -> {
                viewModelScope.launch {
                    _mainScreenState.value =
                        _mainScreenState.value.copy(switchValue = !_mainScreenState.value.switchValue)
                }
            }

            is MainScreenEvent.TilePressed -> {

                val eventTileX = event.pair.first
                val eventTileY = event.pair.second

                val greenTileX = _mainScreenState.value.greenTile.first
                val greenTileY = _mainScreenState.value.greenTile.second

                val redTileX = _mainScreenState.value.redTile.first
                val redTileY = _mainScreenState.value.redTile.second

                val switch = _mainScreenState.value.switchValue

                if (switch) {
                    if (eventTileX != greenTileX || eventTileY != greenTileY) {
                        viewModelScope.launch {
                            if (greenTileX != -1 && greenTileY != -1) {
                                _mainScreenState.value.chessboard[greenTileX][greenTileY] =
                                    startingTileColor(greenTileX, greenTileY)
                            }
                            _mainScreenState.value.chessboard[eventTileX][eventTileY] =
                                0xFF00FF00
                            val newChessboard = _mainScreenState.value.chessboard
                            _mainScreenState.value = _mainScreenState.value.copy(
                                greenTile = event.pair,
                                chessboard = newChessboard
                            )
                        }
                    } else {
                        viewModelScope.launch {
                            _mainScreenState.value.chessboard[eventTileX][eventTileY] =
                                startingTileColor(eventTileX, eventTileY)
                            val newChessboard = _mainScreenState.value.chessboard
                            _mainScreenState.value = _mainScreenState.value.copy(
                                greenTile = -1 to -1,
                                chessboard = newChessboard
                            )
                        }
                    }
                } else {
                    if (eventTileX != redTileX || eventTileY != redTileY) {
                        viewModelScope.launch {
                            if (redTileX != -1 && redTileY != -1) {
                                _mainScreenState.value.chessboard[redTileX][redTileY] =
                                    startingTileColor(redTileX, redTileY)
                            }
                            _mainScreenState.value.chessboard[eventTileX][eventTileY] =
                                0xFFFF0000
                            val newChessboard = _mainScreenState.value.chessboard
                            _mainScreenState.value = _mainScreenState.value.copy(
                                redTile = event.pair,
                                chessboard = newChessboard
                            )
                        }
                    } else {
                        viewModelScope.launch {
                            _mainScreenState.value.chessboard[event.pair.first][event.pair.second] =
                                startingTileColor(event.pair.first, event.pair.second)
                            val newChessboard = _mainScreenState.value.chessboard
                            _mainScreenState.value = _mainScreenState.value.copy(
                                redTile = -1 to -1,
                                chessboard = newChessboard
                            )
                        }
                    }
                }
            }

            MainScreenEvent.ButtonPress -> {
                viewModelScope.launch {
                    if (_mainScreenState.value.greenTile != -1 to -1 &&
                        _mainScreenState.value.redTile != -1 to -1
                    ) {
                        val paths = knightPathsUseCase.findPathsAsync(
                            size = _mainScreenState.value.sliderValue.toInt(),
                            start = _mainScreenState.value.greenTile,
                            end = _mainScreenState.value.redTile,
                            maxDepth = _mainScreenState.value.maxDepth
                        )
                        _mainScreenState.value = _mainScreenState.value.copy(paths = paths)
                        mainScreenStateRepository.insertScreenState(_mainScreenState.value)
                    }
                }
            }

            is MainScreenEvent.MaxNumberOfMovesChanged -> {
                viewModelScope.launch {
                    _mainScreenState.value = _mainScreenState.value.copy(maxDepth = event.moves)
                }
            }

            MainScreenEvent.ResetButtonPress -> {
                viewModelScope.launch {
                    val size = mainScreenState.value.sliderValue.toInt()
                    val newChessboard = Array(size) { i ->
                        Array(size) { j ->
                            startingTileColor(i, j)
                        }
                    }
                    _mainScreenState.value = _mainScreenState.value.copy(
                        redTile = -1 to -1,
                        greenTile = -1 to -1,
                        sliderValue = size.toFloat(),
                        maxDepth = 3,
                        switchValue = true,
                        chessboard = newChessboard,
                        paths = emptyList()
                    )
                }
            }
        }
    }

    private fun startingTileColor(i: Int, j: Int): Long {
        return if ((i + j) % 2 == 0) 0xFFCCCCCC else 0xFF444444
    }

}