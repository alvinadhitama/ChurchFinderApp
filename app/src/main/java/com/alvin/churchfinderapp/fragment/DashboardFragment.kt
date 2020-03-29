package com.alvin.churchfinderapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.adapter.PopularAdapter
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.*
import com.alvin.churchfinderapp.adapter.AnotherAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.model.Popular
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var  mDatabase:DatabaseReference
    lateinit var pDatabase:DatabaseReference
    private var dataList = ArrayList<Church>()
    private var dataListP = ArrayList<Popular>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Church")
        pDatabase = FirebaseDatabase.getInstance().getReference("Popular")

        tv_name.setText(preferences.getValues("name"))
        tv_username.setText(preferences.getValues("username"))

        Glide.with(this)
            .load(preferences.getValues("photo"))
            .apply(RequestOptions.centerCropTransform())
            .into(iv_profile_dashboard)

        iv_search.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        tv_see_all.setOnClickListener {
            startActivity(Intent(activity, ListChurchActivity::class.java))
        }

        btn_to_fav.setOnClickListener {
            startActivity(Intent(activity, FavoriteActivity::class.java))
        }

        rv_popular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getData()

        rv_another.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getDataPopular()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val church = getdataSnapshot.getValue(Church::class.java!!)
                    dataList.add(church!!)
                }

                rv_another.adapter = AnotherAdapter(dataList) {
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getDataPopular() {
        pDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataListP.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val popular = getdataSnapshot.getValue(Popular::class.java!!)
                    dataListP.add(popular!!)
                }

                rv_popular.adapter = PopularAdapter(dataListP){
                    val intent = Intent(context, DetailPopularActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}


