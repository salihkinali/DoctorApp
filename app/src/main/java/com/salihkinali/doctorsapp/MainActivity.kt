package com.salihkinali.doctorsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.salihkinali.doctorsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment

        navHostFragment.findNavController().run {
            binding.materialToolbar.setupWithNavController(this, AppBarConfiguration(graph))
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.baseFragment -> binding.materialToolbar.title = "Doctor App"
                R.id.premiumFragment -> binding.materialToolbar.title = "Randevu Ekranı"
                R.id.ordinaryDetailFragment -> binding.materialToolbar.title = "Satın Alma Ekranı"
                else -> binding.materialToolbar.title = ""
            }
        }
    }
}