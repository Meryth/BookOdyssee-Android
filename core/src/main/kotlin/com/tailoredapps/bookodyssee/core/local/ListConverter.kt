package com.tailoredapps.bookodyssee.core.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class ListConverter {

    @TypeConverter
    fun fromStringToList(valueString: String): List<String> =
        Gson().fromJson(valueString, Array<String>::class.java).toList()

    @TypeConverter
    fun fromListToString(list: List<String>): String =
        Gson().toJson(list)
}