package br.com.alura.helloapp.localData.room.typeConverters

import androidx.room.TypeConverter
import java.util.Date


class TypeConverter {

    @TypeConverter
    fun convertDateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun convertLongToDate(longDate: Long?): Date?{

        return longDate?.let {
            long ->
            Date(long)
        }

    }


}