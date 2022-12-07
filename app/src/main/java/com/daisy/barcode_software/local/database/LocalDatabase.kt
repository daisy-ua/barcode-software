package com.daisy.barcode_software.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daisy.barcode_software.local.converters.DateTimeConverter
import com.daisy.barcode_software.local.dao.BarcodeDao
import com.daisy.barcode_software.local.dao.BarcodeInfoDao
import com.daisy.barcode_software.local.models.Barcode
import com.daisy.barcode_software.local.models.BarcodeInfo

@Database(
    entities = [
        Barcode::class,
        BarcodeInfo::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun barcodeDao(): BarcodeDao

    abstract fun barcodeInfoDao(): BarcodeInfoDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            if (INSTANCE == null) {
                synchronized(LocalDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LocalDatabase::class.java,
                            "barcode.db"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}