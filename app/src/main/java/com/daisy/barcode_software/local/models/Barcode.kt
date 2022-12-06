package com.daisy.barcode_software.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Barcode(
    @PrimaryKey val code: String,

    @ColumnInfo(name = "binary_code") val binaryCode: String,

    @ColumnInfo(name = "date_added") val dateAdded: LocalDateTime
)
