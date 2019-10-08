package com.lambdaschool.locationservices.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.api.load
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lambdaschool.locationservices.R
import com.lambdaschool.locationservices.model.Contact
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [.ItemListActivity]
 * in two-pane mode (on tablets) or a [.ItemDetailActivity]
 * on handsets.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ItemDetailFragment : Fragment(), OnMapReadyCallback {
    // TODO: S09M02-4a Add map variable

    private var twoPane: Boolean = false

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the content specified by the fragment
                // arguments.
                // TODO: S09M02-8c get Serializable

                if (activity is ItemDetailActivity) {
                    // single-pane (phone)
                    activity?.toolbar_layout?.title = item?.getFullName()

                    // Load Large profile picture if available
                    item?.picture?.large?.let { largeImage ->

                        // Profile Image on Phone only (single-pane)
                        val profileImage = activity?.profileImage
                        profileImage?.let {
                            it.load(largeImage) {
                                crossfade(true)
                                placeholder(R.drawable.noimage)
                            }
                        }
                    }
                } else {
                    // two-pane (tablet)
                    twoPane = true
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item?.let {
            rootView.bot_item_detail.text = item?.phone
        }

        // TODO: S09M02-4b copy code from generated maps activity into the activity where you want it to live
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        return rootView
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        // TODO: S09M02-4c additional copied code

        // Using known world locations, since lat/lng from API call is random (and usually in the middle of the Pacific...)

        // TODO: S09M02-9 use location data to move the camera and place a pin
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // request the permission
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_LOCATION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == FINE_LOCATION_REQUEST_CODE) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val locationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)
        locationProviderClient.lastLocation.addOnSuccessListener { location ->
            val contactLocation = Location("contact")

            contactLocation.latitude = item!!.location.coordinates.latitude
            contactLocation.longitude = item!!.location.coordinates.longitude

            val distance = location.distanceTo(contactLocation)

            bot_item_detail.text = if (twoPane) {
                getString(R.string.name_distance, item!!.getFullName(), (distance / 1000))
            } else {
                getString(R.string.distance, (distance / 1000))
            }

        }
    }

    companion object {

        private const val FINE_LOCATION_REQUEST_CODE = 5
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"

        private val WORLD_LOCS = listOf(
            LatLng(52.36667, 4.8811446), // Amsterdam, Netherlands
            LatLng(37.9666709, 23.714478), // Athens, Greece
            LatLng(13.7500052, 100.4978113), // Bangkok, Thailand
            LatLng(41.3833373, 2.1478113), // Barcelona, Spain
            LatLng(39.9166708, 116.414478), // Beijing, China
            LatLng(30.0333379, 31.3478113), // Cairo, Egypt
            LatLng(23.1333382, -82.385522), // Havana, Cuba
            LatLng(51.5333366, -0.085522), // London, England
            LatLng(19.4333383, -99.1188554), // Mexico City, Mexico
            LatLng(55.750003, 37.5978113), // Moscow, Russia
            LatLng(48.8589507, 2.2770203), // Paris, France
            LatLng(-9.4374361, 147.1552399), // Port Moresby, Papua New Guinea
            LatLng(-23.5166618, -46.5188554), // São Paulo, Brazil
            LatLng(47.3500036, 8.514478) // Zürich, Switzerland
        )
    }
}
