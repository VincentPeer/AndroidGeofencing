package com.example.geofencing.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geofencing.database.Repository

class GeofenceViewModel(private val repository: Repository) : ViewModel() {
    val allGeofences = repository.allGeofences


}

class GeofenceViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GeofenceViewModel::class.java)) {
            return GeofenceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
