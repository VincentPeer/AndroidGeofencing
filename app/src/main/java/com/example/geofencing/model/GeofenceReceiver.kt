/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceReceiver
 * Description  : GeofenceReceiver will wait to receive a specific event from the system.
 *                  This event corresponds to the location of the system when its gps position
 *                  entered in a registered geofence area.
 */

package com.example.geofencing.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        // Get the event from its corresponding intent and generate a notification
        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        geofencingEvent?.triggeringGeofences?.forEach() {
                GeofenceNotification(context!!).createNotification(it.requestId)
        }
    }
}
