/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceConverter
 * Description  : Contains conversion functions to translate an object type to an other.
 */

package com.example.geofencing.model

class GeofenceConverter {
    companion object {
        /**
         * Return a local Geofence base on a Geofence from the gms.location library.
         */
        fun locationGeofenceToGeofence(geofence: com.google.android.gms.location.Geofence): MyGeofence {
            return MyGeofence(null, geofence.requestId, geofence.latitude, geofence.longitude)
        }
    }
}