package com.alvin.churchfinderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.model.Church
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<Church>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Church")
            .child(data.simple_name.toString())
            .child("photos")

        eng_name.text = data.eng_name
        ind_name.text = data.ind_name
        church_rate.text = data.rating
        church_address.text = data.address
        church_language.text = data.language
        church_contact.text = data.contact
        church_facility.text = data.facility

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        iv_back.setOnClickListener {
            finish()
        }

//        rv_photo_church.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        getData()

    }

//    private fun getData(){
//        mDatabase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                dataList.clear()
//                for (getdataSnapshot in dataSnapshot.getChildren()){
//                    val church = getdataSnapshot.getValue(Photos::class.java!!)
//                    dataList.add(church!!)
//                }
//
//                rv_photo_church.adapter = PhotosAdapter(dataList){}
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@DetailActivity,""+error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}
