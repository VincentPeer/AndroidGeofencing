package com.example.geofencing.database

import com.example.geofencing.model.MyGeofence
import kotlinx.coroutines.CoroutineScope
import kotlin.concurrent.thread

class Repository(private val geofenceDAO: GeofenceDAO, private val scope: CoroutineScope) {
    val allGeofences = geofenceDAO.getAllGeofences()
    val counts = geofenceDAO.getCount()


    fun insertGeofence(myGeofence: MyGeofence) {
        thread {
            geofenceDAO.insert(myGeofence)
        }
    }

    fun updateGeofence(myGeofence: MyGeofence) {
        thread {
            geofenceDAO.update(myGeofence)
        }
    }

    fun deleteGeofence(myGeofence: MyGeofence) {
        thread {
            geofenceDAO.delete(myGeofence)
        }
    }

    fun insertAllGeofence(myGeofences: List<MyGeofence>) {
        thread {
            geofenceDAO.insertAll(*myGeofences.toTypedArray())
        }
    }

    fun deleteAllGeofence() {
        thread {
            geofenceDAO.deleteAll()
        }
    }
}
