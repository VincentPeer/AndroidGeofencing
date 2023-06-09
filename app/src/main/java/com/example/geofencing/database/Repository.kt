package com.example.geofencing.database

import com.example.geofencing.model.MyGeofence
import kotlinx.coroutines.CoroutineScope
import kotlin.concurrent.thread

class Repository(private val geofenceDAO: GeofenceDAO, private val scope: CoroutineScope) {
    val allGeofences = geofenceDAO.getAllGeofences()


    fun insertGeofence(myGeofence: MyGeofence) {
        thread {
            geofenceDAO.insert(myGeofence)
        }
    }
}