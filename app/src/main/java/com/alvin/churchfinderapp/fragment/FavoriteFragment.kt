package com.alvin.churchfinderapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.activity.DetailFavoriteActivity
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.FavoriteActivity
import com.alvin.churchfinderapp.adapter.FavoriteAdapter
import com.alvin.churchfinderapp.model.Favorite
import com.alvin.churchfinderapp.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var  mDatabase:DatabaseReference

    private var dataList = ArrayList<Favorite>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val uid = FirebaseAuth.getInstance().uid ?:""
        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Favorite/"+uid)

//        if (mDatabase != null){
//            rv_favorite.layoutManager = LinearLayoutManager(context!!.applicationContext)
//            getData()
//        }

//        rv_favorite.layoutManager = LinearLayoutManager(context!!.applicationContext)
//        getData()

        btn_to_fav.setOnClickListener {
            startActivity(Intent(activity,FavoriteActivity::class.java))
        }

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val favorite = getdataSnapshot.getValue(Favorite::class.java)
                    dataList.add(favorite!!)
                }

                if (mDatabase != null){
                    rv_favorite.adapter = FavoriteAdapter(dataList){
                        val intent = Intent(context, DetailFavoriteActivity::class.java).putExtra("data",it)
                        startActivity(intent)
                    }
                }
//                rv_favorite.adapter = FavoriteAdapter(dataList){
//                    val intent = Intent(context, DetailFavoriteActivity::class.java).putExtra("data",it)
//                    startActivity(intent)
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}