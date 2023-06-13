/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : MainActivity
 * Description  : Manages the home page, its interaction with the user and the start of the map activity
 */

package com.example.geofencing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geofencing.databinding.ActivityMainBinding
import com.example.geofencing.ui.GeofenceViewModel
import com.example.geofencing.ui.MapActivity
import com.example.geofencing.ui.RecyclerAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val geofenceViewModel: GeofenceViewModel by viewModels {
       GeofenceViewModel.GeofenceViewModelFactory((application as GeofenceApp).repository, application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)

        // Initialize the recyclerView and its adapter
        val recycler = binding.recycleView
        val adapter = RecyclerAdapter(viewModel = geofenceViewModel)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        // Observe geofence items to maintain the list up to date
        geofenceViewModel.allGeofence.observe(this) {
            adapter.items = it
        }

        // New geofence button that start the MapActivity
        binding.addGeofenceBtn.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        // On addGeonfenceBtn long press, add some examples of items in the list
        // These items won't be send to the system for notifications
        binding.addGeofenceBtn.setOnLongClickListener {
            geofenceViewModel.initGeofenceList()
            true
        }
    }
}
