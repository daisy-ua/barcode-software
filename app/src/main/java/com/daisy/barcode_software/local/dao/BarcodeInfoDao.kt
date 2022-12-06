package com.daisy.barcode_software.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daisy.barcode_software.local.models.BarcodeInfo

@Dao
interface BarcodeInfoDao {
    @Query("SELECT * FROM barcode_info WHERE uid == :uid")
    suspend fun getInfoById(uid: String): BarcodeInfo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInfo(info: BarcodeInfo)
}