package com.daisy.barcode_software.ui.registration

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.daisy.barcode_software.constants.Constants.DATE_FORMAT
import com.daisy.barcode_software.databinding.FragmentEmployeeRegistrationBinding
import com.daisy.barcode_software.ui.MainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class EmployeeRegistrationFragment : Fragment() {
    private var binding: FragmentEmployeeRegistrationBinding? = null
    private lateinit var viewModel: RegistrationViewModel

    private val issuedDatePicker = getDatePickerInstance()

    private val expiredDatePicker = getDatePickerInstance()

    private val selectImageFromGalleryResult: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding?.apply {
                    profileImage.setImageURI(uri)
                    profileImage.tag = uri
                    noImageIcon.isVisible = false
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEmployeeRegistrationBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).let { mainActivity ->
            if (mainActivity.isBottomNavigationViewVisible) {
                mainActivity.hideBottomNavigationView()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        (requireActivity() as MainActivity).showBottomNavigationView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }


    private fun setupListeners() {
        binding?.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            issuedDate.setOnClickListener {
                showDatePicker(issuedDatePicker)
            }

            expiredDate.setOnClickListener {
                showDatePicker(expiredDatePicker)
            }

            profileImageLayout.setOnClickListener {
                selectImageFromGallery()
            }

            submitBtn.setOnClickListener {
                validateInputData()
            }
        }

        issuedDatePicker.addOnPositiveButtonClickListener { selection ->
            val formattedDate: String = getFormattedDate(selection)
            binding?.issuedDate?.setText(formattedDate)
        }

        expiredDatePicker.addOnPositiveButtonClickListener { selection ->
            val formattedDate: String = getFormattedDate(selection)
            binding?.expiredDate?.setText(formattedDate)
        }
    }

    private fun validateInputData() {
        Log.d("TAG", binding?.idCode?.text.toString())
        Log.d("TAG", binding?.firstName?.text.toString())
        Log.d("TAG", binding?.lastName?.text.toString())
        Log.d("TAG", binding?.workPosition?.text.toString())
        Log.d("TAG", binding?.issuedDate?.text.toString())
        Log.d("TAG", binding?.expiredDate?.text.toString())
        Log.d("TAG", binding?.profileImage?.tag.toString())
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun getDatePickerInstance(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    private fun showDatePicker(datePicker: MaterialDatePicker<Long>) {
        if (datePicker.isVisible)
            return

        datePicker.show(childFragmentManager, "TAG")
    }

    private fun getFormattedDate(selection: Long): String {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = selection
        val format = SimpleDateFormat(DATE_FORMAT)
        return format.format(calendar.time)
    }
}