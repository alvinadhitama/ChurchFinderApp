package com.alvin.churchfinderapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.activity.DetailActivity
import com.alvin.churchfinderapp.activity.ListChurchActivity
import com.alvin.churchfinderapp.adapter.PopularAdapter
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.activity.SearchActivity
import com.alvin.churchfinderapp.adapter.AnotherAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var  mDatabase:DatabaseReference
    lateinit var firebaseFirestore: FirebaseFirestore
    private var dataList = ArrayList<Church>()

//    private var adapter: ChurchFirestoreRecyclerAdapter?=null

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
        //val fDatabase = FirebaseFirestore.getInstance()

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

        rv_popular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_another.layoutManager = LinearLayoutManager(context!!.applicationContext)
        getData()

        /////////////////////////////////////
//        rv_test.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        val rootRef = FirebaseFirestore.getInstance()
//        val query = rootRef!!.collection("church")
//        val options = FirestoreRecyclerOptions.Builder<Church>()
//            .setQuery(query,Church::class.java).build()
//
//        adapter = ChurchFirestoreRecyclerAdapter(options)
//        rv_test.adapter = adapter

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val church = getdataSnapshot.getValue(Church::class.java!!)
                    dataList.add(church!!)
                }

                rv_popular.adapter = PopularAdapter(dataList){
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data",it)
                    startActivity(intent)
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

//    override fun onStart() {
//        super.onStart()
//        adapter!!.startListening()
//    }

//    override fun onStop() {
//        super.onStop()
//
//        if (adapter!=null){
//            adapter!!.stopListening()
//        }
//    }

//    private inner class ChurchViewHolder internal constructor(private  val view: View):RecyclerView.ViewHolder(view){
//        internal fun setChurch(churchName: String, churchRate: String, churchDisplay:String){
//            val textViewName = view.findViewById<TextView>(R.id.tv_simple_name_popular)
//            val textViewRate = view.findViewById<TextView>(R.id.tv_rate_popular)
//            val tvImage = view.findViewById<ImageView>(R.id.iv_poster_image_popular)
//
//            textViewName.text = churchName
//            textViewRate.text = churchRate
//            Glide.with(this@DashboardFragment)
//                .load(churchDisplay).into(tvImage)
//
//        }
//    }

//    private inner class ChurchFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<Church>) : FirestoreRecyclerAdapter<Church, ChurchViewHolder>(options) {
//        override fun onBindViewHolder(churchViewHolder: ChurchViewHolder, position: Int, churchModel: Church) {
//            churchViewHolder.setChurch(churchModel.simple_name.toString(),churchModel.rating.toString(),churchModel.display.toString())
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChurchViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_popular, parent, false)
//            return ChurchViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ChurchViewHolder, position: Int) {
//            holder.bindItem(data[position], listener, ContextAdapter, position)
//        }
//    }
}


