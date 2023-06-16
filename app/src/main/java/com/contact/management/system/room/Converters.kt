package com.contact.management.system.room

import androidx.room.TypeConverter
import com.contact.management.system.model.UserContact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromString(value: String?): List<UserContact> {
        val listType: Type = object : TypeToken<List<UserContact>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<UserContact>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}