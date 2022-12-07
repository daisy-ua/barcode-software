package com.daisy.barcode_software.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.daisy.barcode_software.databinding.FragmentEmployeeDetailsBinding

class EmployeeDetailsFragment : Fragment() {
    private var binding: FragmentEmployeeDetailsBinding? = null
    private lateinit var viewModel: EmployeeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[EmployeeDetailsViewModel::class.java]

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}