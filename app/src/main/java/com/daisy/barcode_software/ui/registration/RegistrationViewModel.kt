package com.daisy.barcode_software.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daisy.barcode_software.repository.BarcodeRepository

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BarcodeRepository(application)

}