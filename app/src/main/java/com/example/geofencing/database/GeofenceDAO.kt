package com.example.geofencing.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.geofencing.model.Geofence

@Dao
interface GeofenceDAO {

    @Insert
    fun insert(geofence: Geofence): Long

    @Update
    fun update(geofence: Geofence)

    @Delete
    fun delete(geofence: Geofence)

    @Query("SELECT COUNT(*) FROM table_geofencing")
    fun getCount() : LiveData<Long>

    @Query("SELECT * FROM table_geofencing")
    fun getAllGeofences() : LiveData<List<Geofence>>
}