package com.daisy.barcode_software.local.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConverter {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String) : LocalDateTime =
        formatter.parse(value, LocalDateTime::from)

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(dateTime: LocalDateTime) : String =
        dateTime.format(formatter)
}