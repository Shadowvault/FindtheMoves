package com.paris.findthemoves.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paris.findthemoves.R
import com.paris.findthemoves.data.ScreenRepository
import com.paris.findthemoves.di.IoDispatcher
import com.paris.findthemoves.domain.usecases.chessmoves.PathsToChessMovesUseCase
import com.paris.findthemoves.domain.usecases.findpaths.KnightPathsUseCase
import com.paris.findthemoves.presentation.utils.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val knightPathsUseCase: KnightPathsUseCase,
    private val mainScreenStateRepository: ScreenRepository,
    private val pathsToChessMovesUseCase: PathsToChessMovesUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState = _mainScreenState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {

            val restoredState = mainScreenStateRepository.getScreenStateById(1)

            restoredState?.let {
                _mainScreenState.value = _mainScreenState.value.copy(
                    redTile = it.redTile,
                    greenTile = it.greenTile,
                    chessboard = it.chessboard,
                    sliderValue = it.sliderValue,
                    switchValue = it.switchValue,
                    maxDepth = it.maxDepth,
                    foundPathsText = it.foundPathsText,
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
            _mainScreenState.value = _mainScreenState.value.copy(
                switchText = UIText.StringResource(R.string.start_point),
                buttonText = UIText.StringResource(R.string.button),
                resetButtonText = UIText.StringResource(R.string.reset_button)
            )
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.SliderValueChanged -> {
                viewModelScope.launch(dispatcher) {
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
                viewModelScope.launch(dispatcher) {
                    _mainScreenState.value =
                        _mainScreenState.value.copy(switchValue = !_mainScreenState.value.switchValue)
                    if (_mainScreenState.value.switchValue) {
                        _mainScreenState.value =
                            _mainScreenState.value.copy(switchText = UIText.StringResource(R.string.start_point))
                    } else {
                        _mainScreenState.value =
                            _mainScreenState.value.copy(switchText = UIText.StringResource(R.string.end_point))
                    }
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
                        viewModelScope.launch(dispatcher) {
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
                        viewModelScope.launch(dispatcher) {
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
                        viewModelScope.launch(dispatcher) {
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
                        viewModelScope.launch(dispatcher) {
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
                viewModelScope.launch(dispatcher) {
                    if (_mainScreenState.value.greenTile != -1 to -1 &&
                        _mainScreenState.value.redTile != -1 to -1
                    ) {
                        val paths = knightPathsUseCase.findPathsAsync(
                            size = _mainScreenState.value.sliderValue.toInt(),
                            start = _mainScreenState.value.greenTile,
                            end = _mainScreenState.value.redTile,
                            maxDepth = _mainScreenState.value.maxDepth
                        )
                        if (paths.isEmpty()) {
                            _mainScreenState.value =
                                _mainScreenState.value.copy(foundPathsText = UIText.StringResource(R.string.paths_not_found))
                        } else {
                            _mainScreenState.value = _mainScreenState.value.copy(
                                foundPathsText = UIText.DynamicString(
                                    pathsToChessMovesUseCase.convertPathsToChessMoves(
                                        paths = paths,
                                        size = _mainScreenState.value.chessboard.size
                                    )
                                )
                            )
                        }
                        _mainScreenState.value = _mainScreenState.value.copy(paths = paths)
                        mainScreenStateRepository.insertScreenState(_mainScreenState.value)
                    }
                }
            }

            is MainScreenEvent.MaxNumberOfMovesChanged -> {
                viewModelScope.launch(dispatcher) {
                    _mainScreenState.value = _mainScreenState.value.copy(maxDepth = event.moves)
                }
            }

            MainScreenEvent.ResetButtonPress -> {
                viewModelScope.launch(dispatcher) {
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
                        paths = emptyList(),
                        foundPathsText = UIText.DynamicString("")
                    )
                }
            }
        }
    }

    private fun startingTileColor(i: Int, j: Int): Long {
        return if ((i + j) % 2 == 0) 0xFFCCCCCC else 0xFF444444
    }

}