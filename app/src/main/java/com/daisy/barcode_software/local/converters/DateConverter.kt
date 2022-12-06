package com.daisy.barcode_software.local.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String): LocalDate =
        formatter.parse(value, LocalDate::from)

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: LocalDate): String =
        date.format(formatter)
}