package com.daisy.barcode_software.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.ui.setupWithNavController
import com.daisy.barcode_software.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    private fun setupBottomNavigationView() =
//        binding.navigationView.setupWithNavController(navController)

    fun showBottomNavigationView() {
        binding.navigationView.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        binding.navigationView.visibility = View.GONE
    }

    val isBottomNavigationViewVisible
        get() = binding.navigationView.isVisible
}