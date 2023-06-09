package com.example.geofencing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.geofencing.model.MyGeofence
import kotlin.concurrent.thread

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

    private class MyDatabaseCallBack : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                val isEmpty = database.geofenceDao().getCount().value == 0L
                if (isEmpty) {
                    thread {
                        // when the database is created for the 1st time, we can, for example, populate it
                        // should be done asynchronously
                        val dao = database.geofenceDao()
//                        for (i in 0..10) {
//                            dao.insert(Geofence(null, "home", 0, 0))
//                        }
                    }
                }
            }
        }
    }
}
