package com.asirim.mvvmnewsappstudy.data.local

import androidx.room.TypeConverter
import com.asirim.mvvmnewsappstudy.data.dto.Source


class Converter {

    @TypeConverter
    fun fromSourceToString(source: Source): String? = source.name

    @TypeConverter
    fun fromStringToSource(name: String): Source { // TODO (Ahmet) ---> This function will be developed
        return Source(name, name)
    }

}