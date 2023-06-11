package com.example.geofencing.ui

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.geofencing.GeofenceApp
import com.example.geofencing.R
import com.example.geofencing.databinding.ActivityNewGeofenceBinding
import com.example.geofencing.model.GeofenceNotification

class NewGeofenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewGeofenceBinding
    private val geofenceViewModel: GeofenceViewModel by viewModels {
        GeofenceViewModel.GeofenceViewModelFactory((application as GeofenceApp).repository, application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_geofence)

        binding = ActivityNewGeofenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectPositionBtn.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }


    }
}
