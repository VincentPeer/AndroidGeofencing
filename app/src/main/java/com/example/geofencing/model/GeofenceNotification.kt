package com.example.geofencing.model
/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceNotification
 * Description  : Contains functions to create a notification and a channel that we will use to
 *                  show notifications.
 */


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.geofencing.R

class GeofenceNotification(private val context: Context) {
    companion object {
        private val CHANNEL_ID = "geofence"
        private var id = 0
        private val TAG = "GeofenceNotification"
    }



    /**
     * Creates a new channel to receive a notification.
     */
    private fun createNotificationChannel() {
        // Instantiate a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = R.string.channel_name.toString()
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = R.string.channel_description.toString()
            }

            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(context, NotificationManager::class.java)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Creates a notification that will be triggered when the system entered the geofence zone.
     * The notification has a location icon follow by the title of the corresponding geofence
     * and a text message to indicate that the place was reached.
     */
    fun createNotification(PlaceReached: String) {
        createNotificationChannel()
        // Creates a notification for a specific place
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.location_icon)
            .setContentTitle(PlaceReached)
            .setContentText("Location reached!")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()


        with(NotificationManagerCompat.from(context)) {

            // Check notification permissions
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            )
                return
            else // Notify the user if permissions are enables
                notify(++id, notification)
        }
        Log.d(TAG, "New notification created. id=$id")
    }
}
