package com.example.workloadtracker.database.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun to(date: Date?): Long? = date?.time

    @TypeConverter
    fun from(timestamp: Long?): Date? = timestamp?.let { Date(it) }
}
