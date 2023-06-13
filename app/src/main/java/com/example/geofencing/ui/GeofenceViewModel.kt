/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceViewModel
 * Description  : Contains interactions between the repository and the activities.
 */

package com.example.geofencing.ui

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geofencing.database.Repository
import com.example.geofencing.model.GeofenceConverter
import com.example.geofencing.model.GeofenceReceiver
import com.example.geofencing.model.MyGeofence
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.*


/**
 * Manages interactions between the repository and the activities.
 */
class GeofenceViewModel(private val application: Application, private val repository: Repository) : ViewModel() {
    val allGeofence = repository.allGeofences
    private val geofencingClient = LocationServices.getGeofencingClient(application.applicationContext)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(application.applicationContext, GeofenceReceiver::class.java)
        PendingIntent.getBroadcast(application.applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }

    companion object {
        private const val TAG = "GeofenceViewModel"
        private const val GEOFENCE_RADIUS = 100.0f // Circle radius in meter
        private const val EXPIRATION_TIME_IN_MILLIS = 604_800_000L // One week
        private const val NOTIFICATION_RESPONSIVENESS_TIME_IN_MILLIS = 60_0000 // 60 000 == 1 minute
    }


    fun insertGeofence(geofence: MyGeofence) {
        repository.insertGeofence(geofence)
    }

    fun updateGeofence(myGeofence: MyGeofence) {
        repository.updateGeofence(myGeofence)
    }

    fun deleteGeofence(myGeofence: MyGeofence) {
        repository.deleteGeofence(myGeofence)
        removeGeofence(myGeofence.areaName)
    }

    fun insertAllGeofence(myGeofences: List<MyGeofence>) {
        repository.insertAllGeofence(myGeofences)
    }

    fun deleteAllGeofence() {
        repository.deleteAllGeofence()
    }

    /**
     * Instantiates a list of MyGeofence used as example for the reyclerView but not sent to the
     * system for notifications
     */
    fun initGeofenceList() {
        insertAllGeofence(listOf(
            MyGeofence(null, "HEIG-VD Cheseaux", 46.77971564252356, 6.659400234625911),
            MyGeofence(null, "Montanaire", 46.666853374054355, 6.7341835731331035),
            MyGeofence(null, "Pomy", 46.75971989051159, 6.666860456577413),
            MyGeofence(null, "Grandson", 46.80937094525385, 6.645798898475956),
            MyGeofence(null, "Yverdon - Denner", 46.777221780985634, 6.651756017675142),
            MyGeofence(null, "HEIG-VD St Roch", 46.781752416492004, 6.647190077713865),
            MyGeofence(null, "Lamai Thai Food", 46.77847981351364, 6.65411084930997),
            MyGeofence(null, "Plage d'Yverdon", 46.78501389798879, 6.652649941251356),
        ))
    }

    /**
     * Create a new geofence by asking the system to look after a circular area. The system has to
     * notify this app if the position is inside this area.
     */
    fun newGeofence(title: String, latLng: LatLng) {
        val geofence = Geofence.Builder()
            .setRequestId(title)
            .setCircularRegion(latLng.latitude, latLng.longitude, GEOFENCE_RADIUS)
            .setExpirationDuration(EXPIRATION_TIME_IN_MILLIS)
            .setNotificationResponsiveness(NOTIFICATION_RESPONSIVENESS_TIME_IN_MILLIS)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        //val geofencingClient = LocationServices.getGeofencingClient(application.applicationContext)

        val geofencingRequest = GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()

        val myGeofence = GeofenceConverter.locationGeofenceToGeofence(geofence)
        insertGeofence(myGeofence)

        if (ActivityCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
            return

        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                // Geofences added
                Log.d(TAG, "Geofence successfully added")
            }
            addOnFailureListener {
                // Failed to add geofences
                Log.d(TAG, "Geofence failed to be added")
            }
        }
    }

    /**
     * Ask the system to remove a geofence with a specified requestId
     */
    private fun removeGeofence(requestId: String) {
        geofencingClient.removeGeofences(listOf(requestId)).run {
            addOnSuccessListener {
                // Geofences removed
                Log.d(TAG, "Geofence $requestId successfully removed")
            }
            addOnFailureListener {
                // Failed to remove geofences
                Log.d(TAG, "Geofence $requestId failed to be removed")
            }
        }
    }

    /**
     * Return the address of a given coordinate
     */
    fun getAddressFromLatLng(latLng: LatLng): String? {
        val geocoder = Geocoder(application.applicationContext, Locale.getDefault());
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        return addresses!![0].getAddressLine(0)
    }


    class GeofenceViewModelFactory(private val repository: Repository, private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(GeofenceViewModel::class.java)) {
                return GeofenceViewModel(application, repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
