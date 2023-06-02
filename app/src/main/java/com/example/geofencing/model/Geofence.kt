package com.example.geofencing.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_geofencing")
data class Geofence(
    @PrimaryKey var geofenceId : Long,
    @ColumnInfo(name = "Area name") var areaName: String,
    @ColumnInfo(name = "Latitude arey") var latitude: String,
    @ColumnInfo(name = "Longitude area") var longitude: String,
)


