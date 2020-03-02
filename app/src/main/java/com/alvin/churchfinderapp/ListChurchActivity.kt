package com.alvin.churchfinderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.adapter.AnotherAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_church.*
import java.util.ArrayList

class ListChurchActivity : AppCompatActivity() {

    private lateinit var preference: Preferences
    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Church>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_church)

        preference = Preferences(this)
        mDatabase = FirebaseDatabase.getInstance().getReference("Church")

        rv_list_church.layoutManager = LinearLayoutManager(this)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val church = getdataSnapshot.getValue(Church::class.java!!)
                    dataList.add(church!!)
                }

                rv_list_church.adapter = AnotherAdapter(dataList){
                    val intent = Intent(this@ListChurchActivity, DetailActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListChurchActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
