package com.paris.findthemoves.data.utils

import androidx.room.TypeConverter

class ListOfListOfPairsTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<List<Pair<Int, Int>>> {
        return value.split(";").mapNotNull { part ->
            part.split(",").mapNotNull { pair ->
                pair.split(":").takeIf { it.size == 2 }?.let { components ->
                    Pair(components[0].toInt(), components[1].toInt())
                }
            }.takeIf { it.isNotEmpty() }
        }
    }

    @TypeConverter
    fun fromList(list: List<List<Pair<Int, Int>>>): String {
        return list.joinToString(";") { innerList ->
            innerList.joinToString(",") { "${it.first}:${it.second}" }
        }
    }
}