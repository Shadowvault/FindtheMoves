package com.paris.findthemoves.data

import com.paris.findthemoves.presentation.MainScreenState

class MainScreenStateRepositoryImpl(
    private val dao: ScreenDAO
) : ScreenRepository {
    override suspend fun insertScreenState(screenState: MainScreenState) {
        dao.insertScreenState(
            MainScreenStateEntity(
                id = 1,
                redTile = screenState.redTile,
                greenTile = screenState.greenTile,
                sliderValue = screenState.sliderValue,
                maxDepth = screenState.maxDepth,
                switchValue = screenState.switchValue,
                chessboard = screenState.chessboard,
                paths = screenState.paths
            )
        )
    }

    override suspend fun getScreenStateById(screenStateId: Int): MainScreenState? {
        return dao.getScreenStateById(1)?.toMainScreenState()
    }

}