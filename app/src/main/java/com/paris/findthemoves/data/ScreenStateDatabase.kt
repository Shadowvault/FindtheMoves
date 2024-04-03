package com.paris.findthemoves.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paris.findthemoves.data.utils.Array2DTypeConverter
import com.paris.findthemoves.data.utils.ListOfListOfPairsTypeConverter
import com.paris.findthemoves.data.utils.PairTypeConverter

@Database(
    entities = [MainScreenStateEntity::class],
    version = 1
)
@TypeConverters(PairTypeConverter::class, Array2DTypeConverter::class, ListOfListOfPairsTypeConverter::class)
abstract class ScreenStateDatabase: RoomDatabase() {

    abstract val dao: ScreenDAO
}