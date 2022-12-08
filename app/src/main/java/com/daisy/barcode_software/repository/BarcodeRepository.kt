package com.daisy.barcode_software.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.daisy.barcode_software.local.dao.BarcodeDao
import com.daisy.barcode_software.local.dao.BarcodeInfoDao
import com.daisy.barcode_software.local.database.LocalDatabase
import com.daisy.barcode_software.local.models.Barcode
import com.daisy.barcode_software.local.models.BarcodeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BarcodeRepository(application: Application) {
    private var barcodeDao: BarcodeDao
    private var barcodeInfoDao: BarcodeInfoDao

    init {
        val db = LocalDatabase.getInstance(application)
        barcodeDao = db.barcodeDao()
        barcodeInfoDao = db.barcodeInfoDao()
    }

    fun getAllBarcodes(): LiveData<List<Barcode>> =
        barcodeDao.getAllBarcodes()

    suspend fun getBarcodeBinary(key: String): String =
        withContext(Dispatchers.IO) {
            barcodeDao.getBarcodeBinary(key)
        }

    suspend fun getBarcodeBinaryById(id: String): String =
        withContext(Dispatchers.IO) {
            barcodeDao.getBarcodeBinaryById(id)
        }

    suspend fun getBarcodeInfo(key: String): BarcodeInfo =
        withContext(Dispatchers.IO) {
            barcodeInfoDao.getInfoById(key)
        }

    suspend fun insertBarcode(barcode: Barcode, barcodeInfo: BarcodeInfo) =
        withContext(Dispatchers.IO) {
            barcodeDao.insertBarcode(barcode)
            barcodeInfoDao.insertInfo(barcodeInfo)
        }
}