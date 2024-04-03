package com.paris.findthemoves.data.utils

import androidx.room.TypeConverter

class ListOfListOfPairsTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<List<Pair<Int, Int>>> {
        val list = mutableListOf<List<Pair<Int, Int>>>()
        val parts = value.split(";") // Assuming ';' as delimiter
        for (part in parts) {
            val innerList = mutableListOf<Pair<Int, Int>>()
            val pairs = part.split(",")
            for (pair in pairs) {
                val components = pair.split(":")
                if (components.size == 2) {
                    val first = components[0].toInt()
                    val second = components[1].toInt()
                    innerList.add(Pair(first, second))
                }
            }
            if (innerList.isNotEmpty()) {
                list.add(innerList)
            }
        }
        return list
    }

    @TypeConverter
    fun fromList(list: List<List<Pair<Int, Int>>>): String {
        val stringBuilder = StringBuilder()
        for (innerList in list) {
            for ((index, pair) in innerList.withIndex()) {
                stringBuilder.append("${pair.first}:${pair.second}")
                if (index < innerList.size - 1) {
                    stringBuilder.append(",")
                }
            }
            stringBuilder.append(";")
        }
        return stringBuilder.toString()
    }
}