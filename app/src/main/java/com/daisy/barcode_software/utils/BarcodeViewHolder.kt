package com.daisy.barcode_software.utils

import androidx.recyclerview.widget.RecyclerView
import com.daisy.barcode_software.databinding.ViewHolderBarcodeListBinding
import com.daisy.barcode_software.local.models.Barcode

class BarcodeViewHolder(private val binding: ViewHolderBarcodeListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(barcode: Barcode) = binding.apply {
        idCode.text = barcode.code
    }
}