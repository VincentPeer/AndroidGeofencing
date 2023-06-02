package com.example.geofencing.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.geofencing.model.Geofence


@Database(entities = [Geofence::class],
    version = 1,
    exportSchema = true)
abstract class GeofenceDatabase : RoomDatabase() {

    abstract fun geofenceDao() : GeofenceDAO

    companion object {
        private var INSTANCE:GeofenceDatabase? = null

        fun getDatabase(context: Context) : GeofenceDatabase {
            return INSTANCE?: synchronized(this) {
                INSTANCE!!
            }
        }
    }

}