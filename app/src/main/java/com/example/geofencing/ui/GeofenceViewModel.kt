package com.example.geofencing.ui

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geofencing.R
import com.example.geofencing.database.Repository
import com.example.geofencing.model.GeofenceConverter
import com.example.geofencing.model.GeofenceReceiver
import com.example.geofencing.model.MyGeofence
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.*


class GeofenceViewModel(private val application: Application, private val repository: Repository) : ViewModel() {
    val allGeofence = repository.allGeofences
    val counts = repository.counts

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(application.applicationContext, GeofenceReceiver::class.java)
        PendingIntent.getBroadcast(application.applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }


    fun insertGeofence(geofence: MyGeofence) {
        repository.insertGeofence(geofence)
    }

    fun updateGeofence(myGeofence: MyGeofence) {
        repository.updateGeofence(myGeofence)
    }

    fun deleteGeofence(myGeofence: MyGeofence) {
        repository.deleteGeofence(myGeofence)
    }

    fun insertAllGeofence(myGeofences: List<MyGeofence>) {
        repository.insertAllGeofence(myGeofences)
    }

    fun deleteAllGeofence() {
        repository.deleteAllGeofence()
    }

    fun initGeofenceList() {
        insertAllGeofence(listOf(MyGeofence(null, "Home", 46.540555865486326, 6.811297206581053),
            MyGeofence(null, "HEIG-VD Cheseaux", 46.77971564252356, 6.659400234625911),
            MyGeofence(null, "Montanaire", 46.666853374054355, 6.7341835731331035),
            MyGeofence(null, "Moudon", 46.670322327394, 6.7967575165610095),
            MyGeofence(null, "Pomy", 46.75971989051159, 6.666860456577413),
            MyGeofence(null, "Grandson", 46.80937094525385, 6.645798898475956),
            MyGeofence(null, "Yverdon - Denner", 46.777221780985634, 6.651756017675142),
            MyGeofence(null, "HEIG-VD St Roch", 46.781752416492004, 6.647190077713865),
            MyGeofence(null, "Lamai Thai Food", 46.77847981351364, 6.65411084930997),
            MyGeofence(null, "Plage d'Yverdon", 46.78501389798879, 6.652649941251356),
        ))
    }



    fun newGeofence(title: String, latLng: LatLng) {
        val radius = 100.0f // unit is in meter
        val expirationTimeInMillis = 604800000L // one weed
        val notifResponsivnessTimeInMillis = 60_0000 // 60 000 == 1 minutes
        //geofenceList.add
        val geofence = Geofence.Builder()
            .setRequestId(title)
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setExpirationDuration(expirationTimeInMillis)
            .setNotificationResponsiveness(notifResponsivnessTimeInMillis)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val geofencingClient = LocationServices.getGeofencingClient(application.applicationContext)

        val geofencingRequest = GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()

        val myGeofence = GeofenceConverter.locationGeofenceToGeofence(geofence)
        repository.insertGeofence(myGeofence)

        if (ActivityCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnFailureListener {
                // display error
            }
            addOnSuccessListener {
                // move on
            }
        }
    }

    fun getAdressFromLatLng(latLng: LatLng): String? {
        val geocoder = Geocoder(application.applicationContext, Locale.getDefault());

        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        return addresses?.get(0)?.getAddressLine(0)
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


