/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceDAO
 * Description  : Contains functions to send requests to the database
 */


package com.example.geofencing.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.geofencing.model.MyGeofence

@Dao
interface GeofenceDAO {

    @Insert
    fun insert(myGeofence: MyGeofence): Long

    @Update
    fun update(myGeofence: MyGeofence)

    @Delete
    fun delete(myGeofence: MyGeofence)

    @Query("SELECT COUNT(*) FROM table_geofencing")
    fun getCount() : LiveData<Long>

    @Query("SELECT * FROM table_geofencing")
    fun getAllGeofences() : LiveData<List<MyGeofence>>

    @Insert
    fun insertAll(vararg geofence: MyGeofence)

    @Query("DELETE FROM table_geofencing")
    fun deleteAll()
}