package com.daisy.barcode_software.ui.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daisy.barcode_software.core.Code39Decoder
import com.daisy.barcode_software.repository.BarcodeRepository
import kotlinx.coroutines.launch

class ScannerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BarcodeRepository(application)
    private var decoder: Code39Decoder? = null

    val idCode: String? get() = decoder?.decodedString
    val checkDigit: Char? get() = decoder?.checkDigit

    private var zxingBarcode: String? = null
    private var binaryData: String? = null

    private val _isRegistered: MutableLiveData<Registration> = MutableLiveData(Registration.NONE)
    val isRegistered: LiveData<Registration> get() = _isRegistered

    val isValidType: Boolean get() = !zxingBarcode.isNullOrEmpty()

    fun setZxingBarcode(value: String) {
        zxingBarcode = value
    }

    fun checkIfExist() = viewModelScope.launch {
        val ifExist = !zxingBarcode?.let { key ->
            repository.getBarcodeBinaryById(key).also { codeString ->
                binaryData = codeString
            }
        }.isNullOrBlank()

        _isRegistered.value = Registration.values().firstOrNull() { it.value == ifExist }
    }

    fun reset() {
        _isRegistered.value = Registration.NONE
        zxingBarcode = null
        binaryData = null
        decoder = null
    }

    fun decode() {
        decoder = Code39Decoder()
        binaryData?.let { binary ->
            decoder!!.decode(binary)
        }
    }
}

enum class Registration(val value: Boolean?) {
    NONE(null), TRUE(true), FALSE(false)
}