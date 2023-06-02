package com.example.geofencing

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.geofencing.databinding.ActivityMainBinding
import com.example.geofencing.ui.NewGeofenceActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createAlarm.setOnClickListener {
            startActivity(Intent(this, NewGeofenceActivity::class.java))
        }
    }

}