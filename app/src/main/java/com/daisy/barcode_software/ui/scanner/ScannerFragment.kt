package com.daisy.barcode_software.ui.scanner

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.daisy.barcode_software.R
import com.daisy.barcode_software.databinding.FragmentScannerBinding
import com.daisy.barcode_software.utils.convertUriToBitmap
import com.google.zxing.BinaryBitmap
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.oned.Code39Reader

class ScannerFragment : Fragment() {
    private var binding: FragmentScannerBinding? = null
    private lateinit var viewModel: ScannerViewModel

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                readBarcode(uri)
                binding?.apply {
                    previewLayout.isVisible = true
                    barcodePreview.setImageURI(uri)
                    uploadFileBtn.isVisible = false
                }
                if (viewModel.isValidType) {
                    viewModel.checkIfExist()
                }
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch(arrayOf(
        "image/*"
    ))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ScannerViewModel::class.java]
        setupListeners()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupListeners() {
        binding?.apply {
            backBtn.setOnClickListener {
                if (binding?.uploadFileBtn?.isVisible == true) {
                    findNavController().popBackStack()
                } else {
                    resetView()
                }
            }

            uploadFileBtn.setOnClickListener {
                selectImageFromGallery()
            }

            showDetailsBtn.setOnClickListener {
                viewModel.idCode?.let { key ->
                    resetView()
                    val action = ScannerFragmentDirections.scannerToDetails(key)
                    findNavController().navigate(action)
                }
            }

            viewModel.isRegistered.observe(viewLifecycleOwner) { isRegistered ->
                if (viewModel.isValidType && isRegistered == Registration.FALSE) {
                    noBarcodesFoundErrMsg.isVisible = true
                    noBarcodesFoundErrMsg.text =
                        requireContext().getString(R.string.not_registered_barcode)
                    return@observe
                }

                if (isRegistered == Registration.TRUE) {
                    successLayout.isVisible = true
                    viewModel.decode()
                    idNum.text = viewModel.idCode
                    checkDigit.text = viewModel.checkDigit.toString()
                }
            }
        }
    }

    private fun resetView() {
        viewModel.reset()
        binding?.apply {
            successLayout.isVisible = false
            noBarcodesFoundErrMsg.isVisible = false
            previewLayout.isVisible = false
            uploadFileBtn.isVisible = true
        }
    }

    private fun readBarcode(uri: Uri) {
        convertUriToBitmap(requireContext(), uri)?.let { bitmap ->
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            bitmap.recycle()

            val source = RGBLuminanceSource(width, height, pixels)
            val bBitmap = BinaryBitmap(HybridBinarizer(source))
            val reader = Code39Reader()

            try {
                viewModel.setZxingBarcode(reader.decode(bBitmap).text)
                binding?.noBarcodesFoundErrMsg?.isVisible = false
            } catch (e: Exception) {
                binding?.noBarcodesFoundErrMsg?.text =
                    requireContext().getString(R.string.no_barcodes_found)
                binding?.noBarcodesFoundErrMsg?.isVisible = true
            }
        }

    }
}