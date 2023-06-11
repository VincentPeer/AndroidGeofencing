package com.example.geofencing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geofencing.databinding.ActivityMainBinding
import com.example.geofencing.model.MyGeofence
import com.example.geofencing.ui.GeofenceViewModel
import com.example.geofencing.ui.NewGeofenceActivity
import com.example.geofencing.ui.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val geofenceViewModel: GeofenceViewModel by viewModels {
       GeofenceViewModel.GeofenceViewModelFactory((application as GeofenceApp).repository, application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.recycleView
        val adapter = RecyclerAdapter(viewModel = geofenceViewModel)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO) {
            geofenceViewModel.deleteAllGeofence()
            Thread.sleep(100)
            geofenceViewModel.initGeofenceList()
        }

        geofenceViewModel.allGeofence.observe(this) {
            adapter.items = it
        }

        binding.addGeofenceBtn.setOnClickListener {
            startActivity(Intent(this, NewGeofenceActivity::class.java))
        }
    }
}
