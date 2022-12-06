package com.daisy.barcode_software.utils

import androidx.recyclerview.widget.DiffUtil
import com.daisy.barcode_software.local.models.Barcode

class BarcodeComparator : DiffUtil.ItemCallback<Barcode>() {
    override fun areItemsTheSame(oldItem: Barcode, newItem: Barcode): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Barcode, newItem: Barcode): Boolean {
        return oldItem == newItem
    }
}