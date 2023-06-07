package com.example.geofencing.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geofencing.R
import com.example.geofencing.databinding.ActivityMainBinding
import com.example.geofencing.databinding.ActivityNewGeofenceBinding

class NewGeofenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewGeofenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_geofence)

        binding = ActivityNewGeofenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openMapBtn.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }
}
