package com.paris.findthemoves.data.utils

import androidx.room.TypeConverter

class PairTypeConverter {
    @TypeConverter
    fun fromPair(pair: Pair<Int, Int>): String {
        return "${pair.first},${pair.second}"
    }

    @TypeConverter
    fun toPair(pairString: String): Pair<Int, Int> {
        val parts = pairString.split(",")
        return Pair(parts[0].toInt(), parts[1].toInt())
    }
}