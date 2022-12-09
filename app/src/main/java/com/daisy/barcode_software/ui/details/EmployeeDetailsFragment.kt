package com.daisy.barcode_software.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.daisy.barcode_software.R
import com.daisy.barcode_software.databinding.FragmentEmployeeDetailsBinding
import com.daisy.barcode_software.local.models.BarcodeInfo
import com.daisy.barcode_software.utils.BarcodePrinter
import com.daisy.barcode_software.utils.printBarcodeToBitmap
import com.daisy.barcode_software.utils.saveImage


class EmployeeDetailsFragment : Fragment() {
    private val args: EmployeeDetailsFragmentArgs by navArgs()

    private var binding: FragmentEmployeeDetailsBinding? = null
    private lateinit var viewModel: EmployeeDetailsViewModel
    private var barcodePrinter: BarcodePrinter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[EmployeeDetailsViewModel::class.java]
        viewModel.getEmployeeInfo(args.uid)
        viewModel.getBarcodeBinary(args.uid)

        barcodePrinter = BarcodePrinter(requireContext())

        setupListeners()

        return binding?.root
    }

    private fun setupView(info: BarcodeInfo) {
        binding!!.apply {
            name.text = listOf(info.firstName, info.lastName).joinToString(separator = " ")
            workPosition.text = info.position
            idNum.text = info.uid
            issuedDate.text = info.issuedDate
            expiredDate.text = info.expiredDate
            idCodePageTitle.text = info.uid

            if (info.imageUrl != "") {
                profileImage.setImageURI(Uri.parse(info.imageUrl))
                noImageIcon.isVisible = false
            }
        }
    }

    private fun setupListeners() {
        viewModel.employeeInfo.observe(viewLifecycleOwner) { info ->
            setupView(info)
        }

        viewModel.barcodeBinary.observe(viewLifecycleOwner) { binary ->
            barcodePrinter?.let { printer ->
                printer.setBinaryData(binary)
                (printer.parent as? ViewGroup)?.removeView(printer)
                binding!!.barcodeView.addView(printer)
            }
        }

        binding?.apply {

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            saveBtn.setOnClickListener {
                viewModel.barcodeBinary.value?.let { binary ->
                    printBarcodeToBitmap(
                        requireContext(),
                        barcodeView.width,
                        barcodeView.height,
                        binary,
                        "*${args.uid}*"
                    )?.let { bitmap ->
                        if (saveImage(requireContext(), bitmap, args.uid)) {
                            saveBtn.setBackgroundResource(R.drawable.ic_download_done_24)
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        barcodePrinter = null
    }
}