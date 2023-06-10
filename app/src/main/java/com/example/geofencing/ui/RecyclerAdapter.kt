package com.example.geofencing.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.geofencing.R
import com.example.geofencing.model.MyGeofence

class RecyclerAdapter(_items : List<MyGeofence> = listOf()) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()  {
    var items = listOf<MyGeofence>()
        set(value) {
            val diffCallback = GeofenceDiffCallback(items, value)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffItems.dispatchUpdatesTo(this)
        }

    init { items = _items }
    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return  GEOFENCE_ALARM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_geofence, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewGeoAlarm = view.findViewById<TextView>(R.id.item_geofence_title)

        fun bind(geofence: MyGeofence) {
            viewGeoAlarm.text = geofence.areaName
        }
    }

    companion object {
        private const val GEOFENCE_ALARM = 1
        private const val CLOCK_ALARM = 2 // Example with features extension for this app
    }
}