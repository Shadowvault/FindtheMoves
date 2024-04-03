package com.paris.findthemoves.data

import com.paris.findthemoves.presentation.MainScreenState

interface ScreenRepository {

    suspend fun insertScreenState(screenState: MainScreenState)

    suspend fun getScreenStateById(screenStateId: Int): MainScreenState?

}