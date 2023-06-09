/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : GeofenceDiffCallback
 * Description  : Determine if two MyGeofence lists or items are equals or not.
 */

package com.example.geofencing.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.geofencing.model.MyGeofence

class GeofenceDiffCallback(private val oldList: List<MyGeofence>, private val newList: List<MyGeofence>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].geofenceId == newList[newItemPosition].geofenceId
    }

    override fun areContentsTheSame(oldItemPosition : Int, newItemPosition : Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class
    }
}