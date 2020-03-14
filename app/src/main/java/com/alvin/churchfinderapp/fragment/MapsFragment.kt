package com.alvin.churchfinderapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alvin.churchfinderapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*


/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment(), OnMapReadyCallback {

    private var mBanteng: Marker?=null
    private var mPugeran: Marker?=null
    private var mPangkalan: Marker?=null
    private var mBaciro: Marker? = null
    private var mKotabaru: Marker? = null
    private var mGanjuran: Marker? = null
    private var mBabarsari: Marker? = null
    private var mPringwulung: Marker? = null

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    lateinit var googleMap: GoogleMap
    lateinit var mMap: GoogleMap
    lateinit var mapFragment : SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkLocationPermission()

        maps.onCreate(savedInstanceState)
        maps.onResume()
        maps.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mMap = it

            val yogyakarta = LatLng(-7.803249, 110.3398253)

            val baciroLocation = LatLng(-7.7913355, 110.3895843)
            mBaciro = mMap.addMarker(MarkerOptions().position(baciroLocation).title("Baciro"))

            val kotabaruLocation = LatLng(-7.788327, 110.371033)
            mKotabaru = mMap.addMarker(MarkerOptions().position(kotabaruLocation).title("Kotabaru"))

            val ganjuranLocation = LatLng(-7.926522, 110.319207)
            mGanjuran = mMap.addMarker(MarkerOptions().position(ganjuranLocation).title("Ganjuran"))

            val babarsariLocation = LatLng(-7.772391, 110.411168)
            mBabarsari = mMap.addMarker(MarkerOptions().position(babarsariLocation).title("Babarsari"))

            val pringwulungLocation = LatLng(-7.769030, 110.393708)
            mPringwulung = mMap.addMarker(MarkerOptions().position(pringwulungLocation).title("Pringwulung"))

            val pangkalanLocation = LatLng(-7.790741, 110.416507)
            mPangkalan = mMap.addMarker(MarkerOptions().position(pangkalanLocation).title("Pangkalan"))

            val pugeranLocation = LatLng(-7.816296, 110.356008)
            mPugeran = mMap.addMarker(MarkerOptions().position(pugeranLocation).title("Pugeran"))

            val bantengLocation = LatLng(-7.740900, 110.391009)
            mBanteng = mMap.addMarker(MarkerOptions().position(bantengLocation).title("Banteng"))
            

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yogyakarta,10f))

            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.uiSettings.isRotateGesturesEnabled = true
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true

            if (ContextCompat.checkSelfPermission(this!!.activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true
            }
        }
    }

    private fun checkLocationPermission():Boolean {
        if (ContextCompat.checkSelfPermission(this!!.activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this!!.activity!!,Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this!!.activity!!, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),LOCATION_PERMISSION_REQUEST_CODE)
            else
                ActivityCompat.requestPermissions(this!!.activity!!, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),LOCATION_PERMISSION_REQUEST_CODE)
            return false
        }else
            return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE->{
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this!!.activity!!,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            mMap!!.isMyLocationEnabled = true
                        }
                }else{
                    Toast.makeText(this!!.activity!!,"Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
