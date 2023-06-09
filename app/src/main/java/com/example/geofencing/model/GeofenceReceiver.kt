package com.example.geofencing.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        Toast.makeText(context, "PLACE REACHED", Toast.LENGTH_SHORT).show()

        geofencingEvent?.triggeringGeofences?.forEach {
            val notiftitle = it.requestId
            Toast.makeText(context, "PLACE REACHED : $notiftitle", Toast.LENGTH_SHORT).show()
            val notification = GeofenceNotification(context!!)
            notification.createNotification(notiftitle)

        }

//        if (geofencingEvent.hasError()) {
//            val errorMessage = GeofenceErrorMessages.getErrorString(this,
//                geofencingEvent.errorCode)
//            // display error
//        } else {
//            geofencingEvent.triggeringGeofences.forEach {
//                val geofence = it.requestId
//                // display notification
//            }
//        }
    }
}