package com.alvin.churchfinderapp.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.PhotosAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.model.Photos
import com.bumptech.glide.Glide
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var church_eng_name:String
    lateinit var church_simple_name :String

    lateinit var mMap: GoogleMap
    lateinit var mapFragment : SupportMapFragment

    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabase2: DatabaseReference
    private var dataList = ArrayList<Photos>()

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback{
            mMap = it

            val churchLocation = LatLng(-7.7913355, 110.3895843)
            mMap.addMarker(MarkerOptions().position(churchLocation).title(church_simple_name))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(churchLocation,15f))
        })

        val data = intent.getParcelableExtra<Church>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Church")
            .child(data.simple_name.toString())
            .child("photos")

        val uid = FirebaseAuth.getInstance().uid ?:""
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("Favorite/"+uid)

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
        getData()

        btn_add_fav.setOnClickListener {
            Toast.makeText(this,"Added to favorite",Toast.LENGTH_LONG).show()
            btn_add_fav.visibility = View.INVISIBLE
            btn_remove.visibility = View.VISIBLE

            mDatabase2 =  FirebaseDatabase.getInstance().getReference("Church/"+church_simple_name)
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
                Toast.makeText(this@DetailActivity,""+error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

//    override fun onMapReady(googleMap: GoogleMap) {
//        //mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val churchMap = LatLng(-7.7913355, 110.3895843)
//        mMap.addMarker(MarkerOptions().position(churchMap).title(church_simple_name))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(churchMap,10f))
//        mMap.minZoomLevel
//    }
}
