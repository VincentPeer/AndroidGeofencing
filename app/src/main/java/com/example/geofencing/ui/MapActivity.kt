package com.example.geofencing.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.geofencing.GeofenceApp
import com.example.geofencing.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {

    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val permissionsGranted = MutableLiveData(false)
    private val viewModels: GeofenceViewModel by viewModels {
        GeofenceViewModelFactory(application = application,
            repository = (application as GeofenceApp).repository
        )
    }




    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // we request permissions
        requestLocationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS))


        // *** IMPORTANT ***
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


    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map

        googleMap?.setOnMapClickListener(this)
        googleMap?.setOnMapLongClickListener(this)


        // Prompt the user for permission.
        // getLocationPermission()
        // [END_EXCLUDE]

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        //getDeviceLocation()
        viewModels.allGeofence.observe(this) { geofences ->
            // Update the cached copy of the words in the adapter.
            for (geofence in geofences) {
                map.addMarker(MarkerOptions()
                    .position(LatLng(geofence.latitude, geofence.longitude))
                    .title(geofence.areaName))
            }
        }
        map.addMarker(MarkerOptions().position(LatLng(46.77, 6.64)).title("Yverdon City"))
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    // [START maps_current_place_location_permission]
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
//        if (ContextCompat.checkSelfPermission(this.applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
//            )
//        }
    }

    private val requestLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        val isFineLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        val isCoarseLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
//        val isBackgroundLocationGranted =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
//                permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false)
//            else
//                true

        if (/*isBackgroundLocationGranted && */(isFineLocationGranted || isCoarseLocationGranted)) {
            // Permission is granted. Continue the action
            permissionsGranted.postValue(true)



        }
        else {
            // Explain to the user that the feature is unavailable
            Toast.makeText(this, R.string.permissions_needed_message, Toast.LENGTH_SHORT).show()
            permissionsGranted.postValue(false)
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    // [START maps_current_place_update_location_ui]
    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
//        var permission : Boolean
//        permissionsGranted.observe(this) { value ->
//            permission = value
//        }
        if (googleMap == null) {
            return
        }
        try {
            if (permissionsGranted.value!!) {
                googleMap?.isMyLocationEnabled = true // Adds marker for the user position
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true // Adds loc button to zoom on user's position
            } else {
                googleMap?.isMyLocationEnabled = true
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true
                //lastKnownLocation = null
                //getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val TAG = "MapActivity"
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mMapView?.onSaveInstanceState(mapViewBundle)
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

    override fun onMapClick(point: LatLng) {
        val taskEditText =  EditText(applicationContext)
        taskEditText.setBackgroundColor(resources.getColor(R.color.purple_500,null))

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

    override fun onMapLongClick(point: LatLng) {
        Log.d(TAG, "mapLongClick : $point")
    }
}
