/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceApp
 * Description  : Application that instantiates the repository
 */

package com.example.geofencing

import android.app.Application
import com.example.geofencing.database.GeofenceDatabase
import com.example.geofencing.database.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GeofenceApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val repository by lazy {
        val database = GeofenceDatabase.getDatabase(this)
        Repository(database.geofenceDao(), applicationScope)
    }
}
