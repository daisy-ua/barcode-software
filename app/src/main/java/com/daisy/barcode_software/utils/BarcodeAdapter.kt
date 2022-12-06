package com.daisy.barcode_software.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.daisy.barcode_software.databinding.ViewHolderBarcodeListBinding
import com.daisy.barcode_software.local.models.Barcode

class BarcodeAdapter(
    private val interaction: Interaction,
) : RecyclerView.Adapter<BarcodeViewHolder>() {
    private var binding: ViewHolderBarcodeListBinding? = null
    private val diffCallback = BarcodeComparator()
    private val differ = AsyncListDiffer(this, diffCallback)

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int = position

    private fun getId(position: Int): String = differ.currentList[position].code

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ViewHolderBarcodeListBinding.inflate(layoutInflater, parent, false)
        return BarcodeViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: BarcodeViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { interaction.onItemClicked(getId(position)) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<Barcode>) = differ.submitList(list)
}