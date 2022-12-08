package com.daisy.barcode_software.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.barcode_software.core.Code39Encoder
import com.daisy.barcode_software.local.models.Barcode
import com.daisy.barcode_software.local.models.BarcodeInfo
import com.daisy.barcode_software.repository.BarcodeRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BarcodeRepository(application)

    private var generator: Code39Encoder? = null

    private val barcodeBinary get() = generator?.encodedData

    fun insertBarcode(
        idCode: String,
        firstName: String,
        lastName: String,
        workPosition: String,
        issuedDate: String,
        expiredDate: String,
        profileImg: String,
    ) {
        barcodeBinary?.let { binary ->
            val barcode = Barcode(idCode, binary, LocalDateTime.now())
            val barcodeInfo = BarcodeInfo(idCode,
                firstName,
                lastName,
                workPosition,
                issuedDate,
                expiredDate,
                profileImg)

            viewModelScope.launch {
                repository.insertBarcode(barcode, barcodeInfo)
            }
        }
    }

    fun generateBarcodeBinary(idCode: String) {
        generator = Code39Encoder(idCode).also {
            it.encode()
        }
    }
}