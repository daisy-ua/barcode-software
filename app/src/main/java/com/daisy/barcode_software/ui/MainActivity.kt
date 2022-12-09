package com.daisy.barcode_software.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.daisy.barcode_software.R
import com.daisy.barcode_software.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ContentMainBinding

    private val navController: NavController by lazy {
        findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id != binding.navigationView.selectedItemId) {
                controller.backQueue.asReversed().drop(1).forEach { entry ->
                    binding.navigationView.menu.forEach { item ->
                        if (entry.destination.id == item.itemId) {
                            item.isChecked = true
                            return@addOnDestinationChangedListener
                        }
                    }
                }
            }
        }
    }

    fun showBottomNavigationView() {
        binding.navigationView.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        binding.navigationView.visibility = View.GONE
    }

    val isBottomNavigationViewVisible
        get() = binding.navigationView.isVisible
}