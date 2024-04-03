package com.paris.findthemoves.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScreenState(screenStateEntity: MainScreenStateEntity)

    @Query("SELECT * FROM mainScreenStateEntity WHERE id = :screenStateId")
    suspend fun getScreenStateById(screenStateId: Int): MainScreenStateEntity?

}