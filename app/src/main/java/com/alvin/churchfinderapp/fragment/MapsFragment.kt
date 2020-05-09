package com.alvin.churchfinderapp.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.MapsSearchActivity
import com.alvin.churchfinderapp.model.Church
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var dataList = ArrayList<Church>()

    private var mBandung: Marker?=null
    private var mBangunharjo: Marker?=null
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
    private var mPadokan: Marker? = null
    private var mPajangan: Marker? = null
    private var mBedog: Marker? = null
    private var mPelem: Marker? = null

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    lateinit var mMap: GoogleMap
    lateinit var mapFragment : SupportMapFragment

    lateinit var mDatabase : DatabaseReference

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

            mDatabase = FirebaseDatabase.getInstance().getReference("Church")
//            val yogyakarta = LatLng(-7.803249, 110.3398253)
            val yogyakarta = LatLng(-7.801390, 110.364760)

            //val yogyakarta = LatLngBounds(LatLng(-7.894871, 110.029249), LatLng(-7.590958, 110.810149))
            //val yogyakarta = LatLngBounds(LatLng(-7.894871, 110.029249), LatLng(-7.678604, 110.527896))
            //val yogyakarta = LatLngBounds(LatLng(-7.870121, 110.066037), LatLng(-7.721044, 110.656858))

            val baciroLocation = LatLng(-7.7913355, 110.3895843)
            mBaciro = mMap.addMarker(MarkerOptions().position(baciroLocation).title("Baciro").snippet("Click here for more info"))

            val kotabaruLocation = LatLng(-7.788327, 110.371033)
            mKotabaru = mMap.addMarker(MarkerOptions().position(kotabaruLocation).title("Kotabaru").snippet("Click here for more info"))

            val ganjuranLocation = LatLng(-7.926522, 110.319207)
            mGanjuran = mMap.addMarker(MarkerOptions().position(ganjuranLocation).title("Ganjuran").snippet("Click here for more info"))

            val babarsariLocation = LatLng(-7.772391, 110.411168)
            mBabarsari = mMap.addMarker(MarkerOptions().position(babarsariLocation).title("Babarsari").snippet("Click here for more info"))

            val pringwulungLocation = LatLng(-7.769030, 110.393708)
            mPringwulung = mMap.addMarker(MarkerOptions().position(pringwulungLocation).title("Pringwulung").snippet("Click here for more info"))

            val pangkalanLocation = LatLng(-7.790741, 110.416507)
            mPangkalan = mMap.addMarker(MarkerOptions().position(pangkalanLocation).title("Pangkalan").snippet("Click here for more info"))

            val pugeranLocation = LatLng(-7.816296, 110.356008)
            mPugeran = mMap.addMarker(MarkerOptions().position(pugeranLocation).title("Pugeran").snippet("Click here for more info"))

            val bantengLocation = LatLng(-7.740900, 110.391009)
            mBanteng = mMap.addMarker(MarkerOptions().position(bantengLocation).title("Banteng").snippet("Click here for more info"))

            val jetisLocation = LatLng(-7.781003, 110.367714)
            mJetis = mMap.addMarker(MarkerOptions().position(jetisLocation).title("Jetis").snippet("Click here for more info"))

            val kumetiranLocation = LatLng(-7.792449, 110.360173)
            mKumetiran = mMap.addMarker(MarkerOptions().position(kumetiranLocation).title("Kumetiran").snippet("Click here for more info"))

            val kidullojiLocation = LatLng(-7.802094, 110.367407)
            mKidulloji = mMap.addMarker(MarkerOptions().position(kidullojiLocation).title("Kidul Loji").snippet("Click here for more info"))

            val bintaranLocation = LatLng(-7.802855, 110.372802)
            mBintaran = mMap.addMarker(MarkerOptions().position(bintaranLocation).title("Bintaran").snippet("Click here for more info"))

            val brayatLocation = LatLng(-7.810075, 110.351104)
            mBrayat = mMap.addMarker(MarkerOptions().position(brayatLocation).title("Brayat Minulya").snippet("Click here for more info"))

            val gampingLocation = LatLng(-7.797909, 110.326228)
            mGamping = mMap.addMarker(MarkerOptions().position(gampingLocation).title("Gamping").snippet("Click here for more info"))

            val nandanLocation = LatLng(-7.754414, 110.367552)
            mNandan = mMap.addMarker(MarkerOptions().position(nandanLocation).title("Nandan").snippet("Click here for more info"))

            val warakLocation = LatLng(-7.718275, 110.336201)
            mWarak = mMap.addMarker(MarkerOptions().position(warakLocation).title("Warak").snippet("Click here for more info"))

            val minomartaniLocation = LatLng(-7.738620, 110.408300)
            mMinomartani = mMap.addMarker(MarkerOptions().position(minomartaniLocation).title("Minomartani").snippet("Click here for more info"))

            val pringgolayanLocation = LatLng(-7.819292, 110.405973)
            mPringgolayan = mMap.addMarker(MarkerOptions().position(pringgolayanLocation).title("Pringgolayan").snippet("Click here for more info"))

            val babadanLocation = LatLng(-7.733030, 110.434907)
            mBabadan = mMap.addMarker(MarkerOptions().position(babadanLocation).title("Babadan").snippet("Click here for more info"))

            val sempuLocation = LatLng(-7.833588, 110.331842)
            mSempu = mMap.addMarker(MarkerOptions().position(sempuLocation).title("Gunung Sempu").snippet("Click here for more info"))

            val sedayuLocation = LatLng(-7.800796, 110.256785)
            mSedayu = mMap.addMarker(MarkerOptions().position(sedayuLocation).title("Sedayu").snippet("Click here for more info"))

            val kalasanLocation = LatLng(-7.772192, 110.466406)
            mKalasan = mMap.addMarker(MarkerOptions().position(kalasanLocation).title("Kalasan").snippet("Click here for more info"))

            val bangunharjoLocation = LatLng(-7.855871, 110.374386)
            mBangunharjo = mMap.addMarker(MarkerOptions().position(bangunharjoLocation).title("Bangunharjo").snippet("Click here for more info"))

            val berbahLocation = LatLng(-7.792380, 110.458410)
            mBerbah = mMap.addMarker(MarkerOptions().position(berbahLocation).title("Berbah").snippet("Click here for more info"))

            val klodranLocation = LatLng(-7.880338, 110.336392)
            mKlodran = mMap.addMarker(MarkerOptions().position(klodranLocation).title("Klodran").snippet("Click here for more info"))

            val boroLocation = LatLng(-7.695624, 110.224173)
            mBoro = mMap.addMarker(MarkerOptions().position(boroLocation).title("Boro").snippet("Click here for more info"))

            val klepuLocation = LatLng(-7.755983, 110.242907)
            mKlepu = mMap.addMarker(MarkerOptions().position(klepuLocation).title("Klepu").snippet("Click here for more info"))

            val medariLocation = LatLng(-7.688824, 110.343512)
            mMedari = mMap.addMarker(MarkerOptions().position(medariLocation).title("Medari").snippet("Click here for more info"))

            val condongcaturLocation = LatLng(-7.754746, 110.408385)
            mCondong = mMap.addMarker(MarkerOptions().position(condongcaturLocation).title("Condong Catur").snippet("Click here for more info"))

            val mlatiLocation = LatLng(-7.735262, 110.362887)
            mMlati = mMap.addMarker(MarkerOptions().position(mlatiLocation).title("Mlati").snippet("Click here for more info"))

            val pakemLocation = LatLng(-7.667593, 110.417597)
            mPakem = mMap.addMarker(MarkerOptions().position(pakemLocation).title("Pakem").snippet("Click here for more info"))

            val promasanLocation = LatLng(-7.665992, 110.234706)
            mPromasan = mMap.addMarker(MarkerOptions().position(promasanLocation).title("Promasan").snippet("Click here for more info"))

            val somohitanLocation = LatLng(-7.635488, 110.387545)
            mSomohitan = mMap.addMarker(MarkerOptions().position(somohitanLocation).title("Somohitan").snippet("Click here for more info"))

            val watesLocation = LatLng(-7.857378, 110.155533)
            mWates = mMap.addMarker(MarkerOptions().position(watesLocation).title("Wates").snippet("Click here for more info"))

            val nanggulanLocation = LatLng(-7.757460, 110.210663)
            mNanggulan = mMap.addMarker(MarkerOptions().position(nanggulanLocation).title("Nanggulan").snippet("Click here for more info"))

            val wonosariLocation = LatLng(-7.971524, 110.606722)
            mWonosari = mMap.addMarker(MarkerOptions().position(wonosariLocation).title("Wonosari").snippet("Click here for more info"))

            val bandungLocation = LatLng(-7.931765, 110.568436)
            mBandung = mMap.addMarker(MarkerOptions().position(bandungLocation).title("Bandung").snippet("Click here for more info"))

            val padokanLocation = LatLng(-7.830970, 110.348095)
            mPadokan = mMap.addMarker(MarkerOptions().position(padokanLocation).title("Padokan").snippet("Click here for more info"))

            val pajanganLocation = LatLng(-7.884599, 110.270787)
            mPajangan = mMap.addMarker(MarkerOptions().position(pajanganLocation).title("Pajangan").snippet("Click here for more info"))

            val bedogLocation = LatLng(-7.756509, 110.341864)
            mBedog = mMap.addMarker(MarkerOptions().position(bedogLocation).title("Bedog").snippet("Click here for more info"))

            val pelemLocation = LatLng(-7.721589, 110.150819)
            mPelem = mMap.addMarker(MarkerOptions().position(pelemLocation).title("Pelem Dukuh").snippet("Click here for more info"))


//            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(yogyakarta, 20))
            //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(yogyakarta, 0))
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yogyakarta,10f))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yogyakarta,12f))

            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.uiSettings.isRotateGesturesEnabled = true
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true

//            mMap.setOnMarkerClickListener { marker ->
//                val markertitle = marker.title
//
//                Toast.makeText(context,markertitle, Toast.LENGTH_SHORT).show()
//                false
//            }

            mMap.setOnInfoWindowClickListener { marker ->
                val markertitle = marker.title

                val intent = Intent(context, MapsSearchActivity::class.java)
                intent.putExtra("data",markertitle)
                startActivity(intent)
            }

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

