package com.example.geofencing.database

class Repository(private val geofenceDAO: GeofenceDAO) {
    val allGeofences = geofenceDAO.getAllGeofences()
}