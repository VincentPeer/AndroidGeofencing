package com.example.geofencing.model

class GeofenceConverter {
    companion object {
        fun locationGeofenceToGeofence(geofence: com.google.android.gms.location.Geofence): MyGeofence {
            return MyGeofence(null, geofence.requestId, geofence.latitude, geofence.longitude)
        }
    }
}