package com.daisy.barcode_software.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.daisy.barcode_software.local.models.Barcode
import com.daisy.barcode_software.repository.BarcodeRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BarcodeRepository(application)

    fun getAllBarcodes(): LiveData<List<Barcode>> = repository.getAllBarcodes()
}