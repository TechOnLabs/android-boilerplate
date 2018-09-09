package com.techonlabs.androidboilerplate.datalayer.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techonlabs.androidboilerplate.datalayer.local.GSON.gson

object GSON {
    val gson = Gson()
}

object DBTypeConverter {
    private val typeToken = object : TypeToken<List<Int>>() {
    }.type

    @TypeConverter
    @JvmStatic
    fun toIntList(value: String): List<Int> {
        return gson.fromJson(value, typeToken)
    }

    @TypeConverter
    @JvmStatic
    fun fromIntList(list: List<Int>): String {
        return gson.toJson(list)
    }

}