package com.daisy.barcode_software.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daisy.barcode_software.local.models.BarcodeInfo
import com.daisy.barcode_software.repository.BarcodeRepository
import kotlinx.coroutines.launch

class EmployeeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BarcodeRepository(application)

    private val _employeeData: MutableLiveData<BarcodeInfo> = MutableLiveData()
    val employeeInfo: LiveData<BarcodeInfo> get() = _employeeData

    private val _barcodeBinary: MutableLiveData<String> = MutableLiveData()
    val barcodeBinary: LiveData<String> get() = _barcodeBinary

    fun getEmployeeInfo(id: String) = viewModelScope.launch {
        _employeeData.value = repository.getBarcodeInfo(id)
    }

    fun getBarcodeBinary(key: String) = viewModelScope.launch {
        _barcodeBinary.value = repository.getBarcodeBinary(key)
    }
}