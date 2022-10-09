package com.jayjson.dinosaurnews.model.typeConverter

import androidx.room.TypeConverter
import com.jayjson.dinosaurnews.model.Country
import com.jayjson.dinosaurnews.model.Language
import com.jayjson.dinosaurnews.model.Source
import com.jayjson.dinosaurnews.model.SourceCategory
import org.json.JSONObject

class SourceTypeConverter {
    @TypeConverter
    fun fromSource(source: Source): String {
        return JSONObject().apply {
            put("id", source.id)
            put("name", source.name)
            put("description", source.description)
            put("url", source.url)
            put("category", source.category)
            put("language", source.language)
            put("country", source.country)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): Source {
        val json = JSONObject(source)
        return Source(
            json.getString("id"),
            json.getString("name"),
            json.getString("description"),
            json.getString("url"),
            json.get("category") as SourceCategory?,
            json.get("language") as Language?,
            json.get("country") as Country?
        )
    }
}