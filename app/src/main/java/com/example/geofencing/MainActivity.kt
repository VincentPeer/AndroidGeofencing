package com.example.geofencing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.geofencing.databinding.ActivityMainBinding
import com.example.geofencing.ui.GeofenceViewModel
import com.example.geofencing.ui.GeofenceViewModelFactory
import com.example.geofencing.ui.NewGeofenceActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val geofenceViewModel: GeofenceViewModel by viewModels {
        GeofenceViewModelFactory((application as GeofenceApp).repository, applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geofenceViewModel.allGeofence.observe(this) { geofences ->
            // Update the cached copy of the words in the adapter.
            geofences.let { geofenceViewModel.allGeofence.value }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createAlarm.setOnClickListener {
            startActivity(Intent(this, NewGeofenceActivity::class.java))
        }
    }

}