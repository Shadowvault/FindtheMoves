package com.paris.findthemoves.fakes

import com.paris.findthemoves.data.ScreenRepository
import com.paris.findthemoves.presentation.MainScreenState

class FakeMainScreenStateRepositoryImpl: ScreenRepository {
    private val list : MutableList<MainScreenState> = mutableListOf()
    override suspend fun insertScreenState(screenState: MainScreenState) {
        list.add(screenState)
    }

    override suspend fun getScreenStateById(screenStateId: Int): MainScreenState? {
        return list.firstOrNull()
    }
}