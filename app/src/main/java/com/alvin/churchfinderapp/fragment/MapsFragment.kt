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
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var mBandung: Marker?=null
    private var mWonosari: Marker?=null
    private var mNanggulan: Marker?=null
    private var mWates: Marker?=null
    private var mSomohitan: Marker?=null
    private var mPromasan: Marker?=null
    private var mPakem: Marker?=null
    private var mMlati: Marker?=null
    private var mCondong: Marker?=null
    private var mMedari: Marker?=null
    private var mKlepu: Marker?=null
    private var mBoro: Marker?=null
    private var mKlodran: Marker?=null
    private var mBerbah: Marker?=null
    private var mKalasan: Marker?=null
    private var mSedayu: Marker?=null
    private var mSempu: Marker?=null
    private var mBabadan: Marker?=null
    private var mPringgolayan: Marker?=null
    private var mMinomartani: Marker?=null
    private var mWarak: Marker?=null
    private var mNandan: Marker?=null
    private var mGamping: Marker?=null
    private var mBrayat: Marker?=null
    private var mBintaran: Marker?=null
    private var mKidulloji: Marker?=null
    private var mKumetiran: Marker?=null
    private var mJetis: Marker?=null
    private var mBanteng: Marker?=null
    private var mPugeran: Marker?=null
    private var mPangkalan: Marker?=null
    private var mBaciro: Marker? = null
    private var mKotabaru: Marker? = null
    private var mGanjuran: Marker? = null
    private var mBabarsari: Marker? = null
    private var mPringwulung: Marker? = null

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

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

            //val yogyakarta = LatLng(-7.803249, 110.3398253)

            val yogyakarta = LatLngBounds(LatLng(-7.894871, 110.029249), LatLng(-7.590958, 110.810149))

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

            val jetisLocation = LatLng(-7.781003, 110.367714)
            mJetis = mMap.addMarker(MarkerOptions().position(jetisLocation).title("Jetis"))

            val kumetiranLocation = LatLng(-7.792449, 110.360173)
            mKumetiran = mMap.addMarker(MarkerOptions().position(kumetiranLocation).title("Kumetiran"))

            val kidullojiLocation = LatLng(-7.802094, 110.367407)
            mKidulloji = mMap.addMarker(MarkerOptions().position(kidullojiLocation).title("Kidul Loji"))

            val bintaranLocation = LatLng(-7.802855, 110.372802)
            mBintaran = mMap.addMarker(MarkerOptions().position(bintaranLocation).title("Bintaran"))

            val brayatLocation = LatLng(-7.810075, 110.351104)
            mBrayat = mMap.addMarker(MarkerOptions().position(brayatLocation).title("Brayat Minulya"))

            val gampingLocation = LatLng(-7.797909, 110.326228)
            mGamping = mMap.addMarker(MarkerOptions().position(gampingLocation).title("Gamping"))

            val nandanLocation = LatLng(-7.754414, 110.367552)
            mNandan = mMap.addMarker(MarkerOptions().position(nandanLocation).title("Nandan"))

            val warakLocation = LatLng(-7.718275, 110.336201)
            mWarak = mMap.addMarker(MarkerOptions().position(warakLocation).title("Warak"))

            val minomartaniLocation = LatLng(-7.738620, 110.408300)
            mMinomartani = mMap.addMarker(MarkerOptions().position(minomartaniLocation).title("Minomartani"))

            val pringgolayanLocation = LatLng(-7.819292, 110.405973)
            mPringgolayan = mMap.addMarker(MarkerOptions().position(pringgolayanLocation).title("Pringgolayan"))

            val babadanLocation = LatLng(-7.733030, 110.434907)
            mBabadan = mMap.addMarker(MarkerOptions().position(babadanLocation).title("Babadan"))

            val sempuLocation = LatLng(-7.833588, 110.331842)
            mSempu = mMap.addMarker(MarkerOptions().position(sempuLocation).title("Gunung Sempu"))

            val sedayuLocation = LatLng(-7.800796, 110.256785)
            mSedayu = mMap.addMarker(MarkerOptions().position(sedayuLocation).title("Sedayu"))

            val kalasanLocation = LatLng(-7.772192, 110.466406)
            mKalasan = mMap.addMarker(MarkerOptions().position(kalasanLocation).title("Kalasan"))

            val berbahLocation = LatLng(-7.792380, 110.458410)
            mBerbah = mMap.addMarker(MarkerOptions().position(berbahLocation).title("Berbah"))

            val klodranLocation = LatLng(-7.880338, 110.336392)
            mKlodran = mMap.addMarker(MarkerOptions().position(klodranLocation).title("Klodran"))

            val boroLocation = LatLng(-7.695624, 110.224173)
            mBoro = mMap.addMarker(MarkerOptions().position(boroLocation).title("Boro"))

            val klepuLocation = LatLng(-7.755983, 110.242907)
            mKlepu = mMap.addMarker(MarkerOptions().position(klepuLocation).title("Klepu"))

            val medariLocation = LatLng(-7.688824, 110.343512)
            mMedari = mMap.addMarker(MarkerOptions().position(medariLocation).title("Medari"))

            val condongcaturLocation = LatLng(-7.754746, 110.408385)
            mCondong = mMap.addMarker(MarkerOptions().position(condongcaturLocation).title("Condong Catur"))

            val mlatiLocation = LatLng(-7.735262, 110.362887)
            mMlati = mMap.addMarker(MarkerOptions().position(mlatiLocation).title("Mlati"))

            val pakemLocation = LatLng(-7.667593, 110.417597)
            mPakem = mMap.addMarker(MarkerOptions().position(pakemLocation).title("Pakem"))

            val promasanLocation = LatLng(-7.665992, 110.234706)
            mPromasan = mMap.addMarker(MarkerOptions().position(promasanLocation).title("Promasan"))

            val somohitanLocation = LatLng(-7.635488, 110.387545)
            mSomohitan = mMap.addMarker(MarkerOptions().position(somohitanLocation).title("Somohitan"))

            val watesLocation = LatLng(-7.857378, 110.155533)
            mWates = mMap.addMarker(MarkerOptions().position(watesLocation).title("Wates"))

            val nanggulanLocation = LatLng(-7.757460, 110.210663)
            mNanggulan = mMap.addMarker(MarkerOptions().position(nanggulanLocation).title("Nanggulan"))

            val wonosariLocation = LatLng(-7.971524, 110.606722)
            mWonosari = mMap.addMarker(MarkerOptions().position(wonosariLocation).title("Wonosari"))

            val bandungLocation = LatLng(-7.931765, 110.568436)
            mBandung = mMap.addMarker(MarkerOptions().position(bandungLocation).title("Bandung"))



            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(yogyakarta, 10))
            //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(yogyakarta, 0))
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yogyakarta,10f))
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yogyakarta.center,10f))

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

