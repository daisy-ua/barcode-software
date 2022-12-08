package com.daisy.barcode_software.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daisy.barcode_software.local.models.Barcode

@Dao
interface BarcodeDao {
    @Query("SELECT * FROM barcode ORDER BY date_added DESC")
    fun getAllBarcodes(): LiveData<List<Barcode>>

    @Query("SELECT binary_code FROM barcode WHERE code == :code")
    suspend fun getBarcodeBinary(code: String): String

    @Query("SELECT binary_code FROM barcode WHERE uid == :uid")
    suspend fun getBarcodeBinaryById(uid: String): String

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBarcode(barcode: Barcode)
}