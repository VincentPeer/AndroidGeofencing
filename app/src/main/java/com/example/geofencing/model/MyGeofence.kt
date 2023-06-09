package com.example.geofencing.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_geofencing")
data class MyGeofence(
    @PrimaryKey(autoGenerate = true) var geofenceId : Long?,
    @ColumnInfo(name = "Area name") var areaName: String,
    @ColumnInfo(name = "Latitude area") var latitude: Double,
    @ColumnInfo(name = "Longitude area") var longitude: Double,
)
