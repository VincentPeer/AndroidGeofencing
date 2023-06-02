package com.example.geofencing.ui

import androidx.lifecycle.ViewModel
import com.example.geofencing.database.Repository

class GeofenceViewModel(private val repository: Repository) : ViewModel() {
    val allGeofences = repository.allGeofences


}