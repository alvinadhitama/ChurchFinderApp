package com.alvin.churchfinderapp.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvin.churchfinderapp.DetailActivity
import com.alvin.churchfinderapp.DetailFavoriteActivity
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.AnotherAdapter
import com.alvin.churchfinderapp.adapter.FavoriteAdapter
import com.alvin.churchfinderapp.adapter.PhotosAdapter
import com.alvin.churchfinderapp.adapter.PopularAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.model.Favorite
import com.alvin.churchfinderapp.model.Photos
import com.alvin.churchfinderapp.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
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

        rv_favorite.layoutManager = LinearLayoutManager(context!!.applicationContext)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val favorite = getdataSnapshot.getValue(Favorite::class.java)
                    dataList.add(favorite!!)
                }

                rv_favorite.adapter =
                    FavoriteAdapter(dataList) {
                        val intent = Intent(context,
                            DetailFavoriteActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
