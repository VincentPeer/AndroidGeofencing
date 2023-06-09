package com.example.geofencing.database

import kotlinx.coroutines.CoroutineScope

class Repository(geofenceDAO: GeofenceDAO, private val scope: CoroutineScope) {
    val allGeofences = geofenceDAO.getAllGeofences()
}