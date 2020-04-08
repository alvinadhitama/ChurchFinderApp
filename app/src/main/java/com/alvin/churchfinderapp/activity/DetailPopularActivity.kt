package com.alvin.churchfinderapp.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.PhotosAdapter
import com.alvin.churchfinderapp.adapter.ScheduleAdapter
import com.alvin.churchfinderapp.model.Photos
import com.alvin.churchfinderapp.model.Popular
import com.alvin.churchfinderapp.model.Schedules
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailPopularActivity : AppCompatActivity() {

    lateinit var church_eng_name:String
    lateinit var church_simple_name :String

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    var churchLatitude :Double = 0.0
    var churchLongitude :Double = 0.0

    lateinit var mMap: GoogleMap
    lateinit var mapFragment : SupportMapFragment

    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabase2: DatabaseReference
    lateinit var mDatabaseSchedule: DatabaseReference
    private var dataList = ArrayList<Photos>()
    private var dataListS = ArrayList<Schedules>()

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popular)

        val data = intent.getParcelableExtra<Popular>("data")
        mDatabase = FirebaseDatabase.getInstance().getReference("Popular")
            .child(data.simple_name.toString())
            .child("photos")

        mDatabaseSchedule = FirebaseDatabase.getInstance().getReference("Popular")
            .child(data.simple_name.toString())
            .child("schedules")

        val uid = FirebaseAuth.getInstance().uid ?:""
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("Favorite/"+uid)

        checkLocationPermission()

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback{
            mMap = it

            churchLatitude = data.latitude!!.toDouble()
            churchLongitude = data.longitude!!.toDouble()

            val churchLocation = LatLng(churchLatitude, churchLongitude)
            mMap.addMarker(MarkerOptions().position(churchLocation).title(church_simple_name))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(churchLocation,15f))

            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.uiSettings.isRotateGesturesEnabled = true
            mMap.uiSettings.isZoomGesturesEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true
            }

        })

        eng_name.text = data.eng_name
        ind_name.text = data.ind_name
        church_rate.text = data.rating
        church_address.text = data.address
        church_language.text = data.language
        church_contact.text = data.contact
        church_facility.text = data.facility

        church_eng_name = eng_name.text.toString()
        church_simple_name = data.simple_name.toString()

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        iv_back.setOnClickListener {
            finish()
        }

        rv_photo_church.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_schedule_church.layoutManager = LinearLayoutManager(this)
        getData()
        getDataS()

        btn_add_fav.setOnClickListener {
            Toast.makeText(this,"Added to favorite",Toast.LENGTH_LONG).show()
            btn_add_fav.visibility = View.INVISIBLE
            btn_remove.visibility = View.VISIBLE

            mDatabase2 =  FirebaseDatabase.getInstance().getReference("Popular/"+church_simple_name)
            mDatabase2.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(databaseError: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    FirebaseDatabase.getInstance().getReference("Favorite/"+uid+"/"+church_simple_name)
                        .setValue(dataSnapshot.value)
                }
            })
        }

        btn_remove.setOnClickListener {
            Toast.makeText(this,"Added to favorite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()){
                    val church = getdataSnapshot.getValue(Photos::class.java!!)
                    dataList.add(church!!)
                }
                rv_photo_church.adapter = PhotosAdapter(dataList){}
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailPopularActivity,""+error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataS(){
        mDatabaseSchedule.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataListS.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()){
                    val church = getdataSnapshot.getValue(Schedules::class.java!!)
                    dataListS.add(church!!)
                }
                rv_schedule_church.adapter = ScheduleAdapter(dataListS){}
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailPopularActivity,""+error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkLocationPermission():Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),LOCATION_PERMISSION_REQUEST_CODE)
            else
                ActivityCompat.requestPermissions(this, arrayOf(
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
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            mMap!!.isMyLocationEnabled = true
                        }
                }else{
                    Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
