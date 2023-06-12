/**
 * HEIV-VD DMA Geofencing project
 * @author      : Dimitri De Bleser, Vincent Peer
 * Date         : 12th june 2023
 * File         : MapActivity
 * Description  : Manages the map view and the actions between the user and the map.
 */

package com.example.geofencing.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.geofencing.GeofenceApp
import com.example.geofencing.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {
    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val permissionsGranted = MutableLiveData(false)
    private val viewModels: GeofenceViewModel by viewModels {
        GeofenceViewModel.GeofenceViewModelFactory(application = application,
            repository = (application as GeofenceApp).repository
        )
    }

    private val requestLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val isFineLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        val isCoarseLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

        if (isFineLocationGranted || isCoarseLocationGranted) {
            // Permission is granted. Continue the action
            permissionsGranted.postValue(true)
        }
        else {
            // Explain to the user that the feature is unavailable
            Toast.makeText(this, R.string.permissions_needed_message, Toast.LENGTH_SHORT).show()
            permissionsGranted.postValue(false)
        }
    }

    companion object {
        private const val TAG = "MapActivity"
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val OYONNAX_LAT = 46.257313836238836
        private const val OYONNAX_LNG = 5.6670217498014255
        private const val BRIENZ_LAT = 46.75339946907581
        private const val BRIENZ_LNG = 8.032496689679895
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Request permissions
        requestLocationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS))


        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView = findViewById<View>(R.id.map) as MapView
        mMapView!!.onCreate(mapViewBundle)
        mMapView!!.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return closeMapActivity()
    }
    private fun closeMapActivity():Boolean {
        finish()
        return true
    }

    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map

        googleMap?.setOnMapClickListener(this)
        googleMap?.setOnMapLongClickListener(this)

        // Open Google Map with location center on specified SW and NE coordinates
        val westernSwitzerlandBounds = LatLngBounds(
            LatLng(OYONNAX_LAT, OYONNAX_LNG),  // SW bounds
            LatLng(BRIENZ_LAT, BRIENZ_LNG) // NE bounds
        )
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(westernSwitzerlandBounds, 0))

        // Adds features to the map
        updateLocationUI()

        // Observes each geofence to mark them on the map
        viewModels.allGeofence.observe(this) { geofences ->
            // Add a marker on the map
            for (geofence in geofences) {
                map.addMarker(MarkerOptions()
                    .position(LatLng(geofence.latitude, geofence.longitude))
                    .title(geofence.areaName))
            }
        }
    }



    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (googleMap == null)
            return

        // Adds marker for the user position
        googleMap?.isMyLocationEnabled = true
        // Adds location button to zoom on user's position
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
    }


    override fun onMapClick(point: LatLng) {
        val taskEditText =  EditText(applicationContext)
        taskEditText.setBackgroundColor(resources.getColor(R.color.gray,null))

        val dialog = AlertDialog.Builder(this)
            .setTitle("New geofencing position")
            .setMessage("Name for this place :")
            .setView(taskEditText)
            .setCancelable(false) // dialog cannot be closed without doing a choice
            .setNegativeButton(android.R.string.cancel) { _,_ ->
                // cancel action
            }
            .setPositiveButton(android.R.string.yes) { _,_ ->
                viewModels.newGeofence(taskEditText.text.toString(), LatLng(point.latitude, point.longitude))
                this.googleMap?.addMarker(MarkerOptions().position(LatLng(point.latitude, point.longitude)).title(taskEditText.text.toString()))
            }
            .create()
        dialog.show()

        Log.d("MapActivity", "mapClick : $point")
    }


    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onStart() {
        super.onStart()
        mMapView!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView!!.onStop()
    }

    override fun onPause() {
        mMapView!!.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMapView!!.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    override fun onMapLongClick(point: LatLng) {
        Log.d(TAG, "mapLongClick at position ($point)")
    }
}
