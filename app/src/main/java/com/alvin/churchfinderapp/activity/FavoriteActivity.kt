package com.alvin.churchfinderapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.FavoriteAdapter
import com.alvin.churchfinderapp.adapter.ListChurchAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.model.Favorite
import com.alvin.churchfinderapp.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_church.*
import java.util.ArrayList

class FavoriteActivity : AppCompatActivity() {

    private lateinit var preference: Preferences
    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Favorite>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        preference = Preferences(this)
        val uid = FirebaseAuth.getInstance().uid ?:""
        mDatabase = FirebaseDatabase.getInstance().getReference("Favorite/"+uid+"/")

        rv_list_church.layoutManager = LinearLayoutManager(this)
        getData()

        iv_back.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val favorite = getdataSnapshot.getValue(Favorite::class.java!!)
                    dataList.add(favorite!!)
                }

                rv_list_church.adapter = FavoriteAdapter(dataList){
                    val intent = Intent(this@FavoriteActivity, DetailFavoriteActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FavoriteActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
