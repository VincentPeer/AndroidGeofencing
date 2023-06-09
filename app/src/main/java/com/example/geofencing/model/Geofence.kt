package com.example.geofencing.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.location.Geofence

@Entity(tableName = "table_geofencing")
data class Geofence(
    @PrimaryKey(autoGenerate = true) var geofenceId : Long?,
    @ColumnInfo(name = "Area name") var areaName: String,
    @ColumnInfo(name = "Latitude area") var latitude: Int,
    @ColumnInfo(name = "Longitude area") var longitude: Int,
)
