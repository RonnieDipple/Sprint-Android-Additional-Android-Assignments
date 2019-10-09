package com.example.mapandtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

    companion object{
        private const val FINE_LOCATION_REQUEST_CODE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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


    private fun getCurrentLocation(){
        //checks permission
        if (ContextCompat.checkSelfPermission(this!!, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //request the permission
            ActivityCompat.requestPermissions(Activity()!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_LOCATION_REQUEST_CODE)
        }else{
            permissionDenied()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        //checks the correct permissions have been granted,
        if(requestCode == FINE_LOCATION_REQUEST_CODE) {
            if(permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }

        }else{
            permissionDenied()
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun permissionDenied(){
        Toast.makeText(this,"Location Permission Denied", Toast.LENGTH_LONG).show()
    }
}
