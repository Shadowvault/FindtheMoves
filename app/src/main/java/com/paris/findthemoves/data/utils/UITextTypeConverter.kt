package com.paris.findthemoves.data.utils

import androidx.room.TypeConverter
import com.paris.findthemoves.presentation.utils.UIText

class UITextTypeConverter {
    @TypeConverter
    fun fromUIText(uiText: UIText): String {
        return when (uiText) {
            is UIText.DynamicString -> uiText.value
            is UIText.StringResource -> uiText.resId.toString() + "|" + uiText.args.joinToString(separator = ",")
        }
    }

    @TypeConverter
    fun toUIText(value: String): UIText {
        val parts = value.split("|")
        return if (parts.size == 1) {
            UIText.DynamicString(parts[0])
        } else {
            val resId = parts[0].toInt()
            val args = parts.subList(1, parts.size).map { it.trim() }.toTypedArray()
            UIText.StringResource(resId, *args)
        }
    }
}