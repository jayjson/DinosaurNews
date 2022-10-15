package com.jayjson.dinosaurnews.model.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jayjson.dinosaurnews.model.Source

class SourceTypeConverter {
    @TypeConverter
    fun toSource(json: String) =
        Gson().fromJson(json, Source::class.java)

    @TypeConverter
    fun fromSource(source: Source) =
        Gson().toJson(source)
}