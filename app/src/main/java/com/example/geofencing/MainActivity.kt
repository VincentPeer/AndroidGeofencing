package com.example.geofencing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geofencing.databinding.ActivityMainBinding
import com.example.geofencing.model.MyGeofence
import com.example.geofencing.ui.GeofenceViewModel
import com.example.geofencing.ui.NewGeofenceActivity
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

        val recycler = binding.recycleView
        val adapter = RecyclerAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        adapter.items = listOf(MyGeofence(null, "Home", 46.540555865486326, 6.811297206581053),
        MyGeofence(null, "HEIG-VD", 46.77971564252356, 6.659400234625911),
        MyGeofence(null, "Montanaire", 46.666853374054355, 6.7341835731331035))



        binding.createAlarm.setOnClickListener {
            startActivity(Intent(this, NewGeofenceActivity::class.java))
        }
    }

}