package com.alvin.churchfinderapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.PhotosAdapter
import com.alvin.churchfinderapp.model.Favorite
import com.alvin.churchfinderapp.model.Photos
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_favorite.*
import kotlinx.android.synthetic.main.activity_detail_favorite.iv_poster

class DetailFavoriteActivity : AppCompatActivity() {

    lateinit var church_eng_name:String
    lateinit var church_simple_name :String

    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_favorite)

        val uid = FirebaseAuth.getInstance().uid ?:""
        val data = intent.getParcelableExtra<Favorite>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Favorite/"+uid)
            .child(data.simple_name.toString())
            .child("photos")

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

        btn_remove_fav.setOnClickListener {
            Toast.makeText(this,"Removed from favorite",Toast.LENGTH_LONG).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            FirebaseDatabase.getInstance().getReference("Favorite/"+uid+"/"+church_simple_name)
                .removeValue()
        }
    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()){
                    val favorite = getdataSnapshot.getValue(Photos::class.java!!)
                    dataList.add(favorite!!)
                }
                rv_photo_church.adapter = PhotosAdapter(dataList){}
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailFavoriteActivity,""+error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}