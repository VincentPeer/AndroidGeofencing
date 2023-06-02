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

    @Query("SELECT * FROM Geofence")
    fun getAllGeofences() : LiveData<List<Geofence>>
}