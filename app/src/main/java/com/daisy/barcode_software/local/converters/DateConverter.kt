package com.daisy.barcode_software.local.converters

import androidx.room.TypeConverter
import com.daisy.barcode_software.constants.Constants.DATE_FORMAT
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateConverter {
    private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String): LocalDate =
        formatter.parse(value, LocalDate::from)

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: LocalDate): String =
        date.format(formatter)
}