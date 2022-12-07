package com.daisy.barcode_software.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barcode_info")
data class BarcodeInfo(
    @PrimaryKey val uid: String,

    @ColumnInfo(name = "first_name") val firstName: String,

    @ColumnInfo(name = "last_name") val lastName: String,

    @ColumnInfo val position: String,

    @ColumnInfo(name = "issued_date") val issuedDate: String,

    @ColumnInfo(name = "expired_date") val expiredDate: String,

    @ColumnInfo(name = "image_url") val imageUrl: String,
)
