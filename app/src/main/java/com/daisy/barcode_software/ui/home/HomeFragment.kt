package com.daisy.barcode_software.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daisy.barcode_software.databinding.FragmentHomeBinding
import com.daisy.barcode_software.utils.BarcodeAdapter
import com.daisy.barcode_software.utils.Interaction

class HomeFragment : Fragment(), Interaction {
    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private val barcodeAdapter = BarcodeAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getAllBarcodes().observe(viewLifecycleOwner) { barcodes ->
            barcodeAdapter.submitList(barcodes)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.barcodesList?.apply {
            adapter = barcodeAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding?.addNewBtn?.setOnClickListener {
            val action = HomeFragmentDirections.homeToRegistration()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClicked(id: String) {
        val action = HomeFragmentDirections.homeToDetails(id)
        findNavController().navigate(action)
    }
}