package com.example.geofencing.ui

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.geofencing.GeofenceApp
import com.example.geofencing.R
import com.example.geofencing.model.MyGeofence
import com.google.android.gms.maps.model.LatLng

class RecyclerAdapter(_items : List<MyGeofence> = listOf(), private val viewModel: GeofenceViewModel) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()  {

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
        if(position % 2== 0)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_500))
        holder.bind(items[position])

        holder.itemView.findViewById<ImageButton>(R.id.delete_icon).setOnClickListener {
            val old_items = items
            viewModel.deleteGeofence(items[position])
            val new_items = viewModel.allGeofence.value
            if(new_items != null) {
                val diffCallback = GeofenceDiffCallback(old_items, new_items)
                val diffItems = DiffUtil.calculateDiff(diffCallback)
                diffItems.dispatchUpdatesTo(this)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewGeofenceTitle = view.findViewById<TextView>(R.id.item_geofence_title)
        private val viewGeofenceAdress = view.findViewById<TextView>(R.id.item_geofence_address)

        fun bind(geofence: MyGeofence) {
            viewGeofenceTitle.text = geofence.areaName
            viewGeofenceAdress.text = viewModel.getAdressFromLatLng(LatLng(geofence.latitude, geofence.longitude))
        }
    }

    companion object {
        private const val GEOFENCE_ALARM = 1
        private const val CLOCK_ALARM = 2 // Example with features extension for this app
    }
}