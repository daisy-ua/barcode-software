package com.daisy.barcode_software.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daisy.barcode_software.repository.BarcodeRepository

class EmployeeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BarcodeRepository(application)

}