package com.example.mapandtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

/*## Overview
Build an app which allows users to place tags on a map and track their current location.

## Requirements
This app will consist of a map acitvity with your own buttons that will center the map on the user's current location
and place a map marker on the centered location.

## Outline
1. Create a project with a maps activity
2. Add a maps api key
3. Add location services permissions
4. Add a button for them to select which will center the map on them
5. Add a button for a new pin at the target location
> use `mMap.getCameraPosition().target` to get the `LatLng` of the center of the map
*/


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {



    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation: Location
    private  val FINE_LOCATION_REQUEST_CODE = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        button_location.isEnabled = false
        button_pinpoint.isEnabled = false

        getCurrentLocation()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        //checks the correct permissions have been granted,
        if(requestCode == FINE_LOCATION_REQUEST_CODE) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }

        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        button_location.setOnClickListener {
            var latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
            // moves the camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        button_pinpoint.setOnClickListener {
            var latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
            // Adds a marker
            mMap.addMarker(MarkerOptions().position(latLng).title("Marker ??"))

        }
    }

    private fun getLocation() {
        var currentLocationResult: Location? = null
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationProviderClient.lastLocation.addOnSuccessListener { location ->
            currentLocationResult = location
            if (currentLocationResult != null) {
                currentLocation = currentLocationResult as Location
              button_location.isEnabled = true
                button_pinpoint.isEnabled = true
            } else {
               permissionDenied()
            }
        }

    }

    private fun getCurrentLocation(){
        //checks permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //request the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_LOCATION_REQUEST_CODE)
        }else{
            getLocation()
        }
    }}

    private fun permissionDenied(){
        Toast.makeText(this,"Location Permission Denied", Toast.LENGTH_LONG).show()
    }
}
