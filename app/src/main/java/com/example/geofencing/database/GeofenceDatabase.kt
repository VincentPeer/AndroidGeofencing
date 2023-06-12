/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceDatabase
 * Description  : Allow the database to be created. Contains a singleton to the database instance.
 */

package com.example.geofencing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.geofencing.model.MyGeofence

@Database(entities = [MyGeofence::class],
    version = 2,
    exportSchema = true)
abstract class GeofenceDatabase : RoomDatabase() {

    abstract fun geofenceDao() : GeofenceDAO

    companion object {
        private var INSTANCE:GeofenceDatabase? = null

        fun getDatabase(context: Context) : GeofenceDatabase {
            return INSTANCE?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    GeofenceDatabase::class.java, "geofence.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(MyDatabaseCallBack())
                    .build()

                INSTANCE!!
            }
        }
    }

    private class MyDatabaseCallBack : Callback() {}
}
