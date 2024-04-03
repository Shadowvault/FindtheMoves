package com.paris.findthemoves.data.utils

import androidx.room.TypeConverter

class Array2DTypeConverter {
    @TypeConverter
    fun fromArray2D(array: Array<Array<Long>>): String {
        return array.joinToString(separator = ";") { it.joinToString(separator = ",") }
    }

    @TypeConverter
    fun toArray2D(data: String): Array<Array<Long>> {
        return data.split(";").map { it.split(",").map { item -> item.toLong() }.toTypedArray() }
            .toTypedArray()
    }
}