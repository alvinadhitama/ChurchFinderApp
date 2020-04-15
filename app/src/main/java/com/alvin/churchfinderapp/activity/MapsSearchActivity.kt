package com.alvin.churchfinderapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.MapsSearchAdapter
import com.alvin.churchfinderapp.adapter.ViewHolderMapsSearch
import com.alvin.churchfinderapp.model.Church
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_maps_search.*

import java.util.ArrayList

class MapsSearchActivity : AppCompatActivity() {

    lateinit var mSearchText : EditText
    lateinit var mTitle :TextView
    lateinit var mRecyclerView : RecyclerView

    lateinit var mDatabase : DatabaseReference

    var context: Context? = null

    private var dataList = ArrayList<Church>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_search)

        val dataIntent = intent.getStringExtra("data")

        mTitle = findViewById(R.id.maps_title)
        mTitle.setText(dataIntent)
        mSearchText =findViewById(R.id.search_maps_text)
        mSearchText.setText(dataIntent)
        mRecyclerView = findViewById(R.id.rv_list_search_maps)
        iv_back.setOnClickListener {
            finish()
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("Church")

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        firebaseSearch(dataIntent)

    }

    private fun firebaseSearch(searchText: String) {
        val firebaseSearchQuery: Query =
            mDatabase.orderByChild("simple_name").startAt(searchText).endAt("$searchText\uf88ff")
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Church, ViewHolderMapsSearch> =
            object : FirebaseRecyclerAdapter<Church, ViewHolderMapsSearch>(
                Church::class.java,
                R.layout.row_item_maps_search,
                ViewHolderMapsSearch::class.java,
                firebaseSearchQuery
            ) {
                override fun populateViewHolder(viewHolder: ViewHolderMapsSearch, church: Church, i: Int) {
                    viewHolder.setDetailsMaps(applicationContext, church.eng_name, church.rating, church.display)

                    firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataList.clear()
                            for (getdataSnapshot in dataSnapshot.getChildren()) {
                                val church = getdataSnapshot.getValue(Church::class.java!!)
                                dataList.add(church!!)
                            }

                            rv_list_search_maps.adapter = MapsSearchAdapter(dataList){
                                val intent = Intent(this@MapsSearchActivity, DetailActivity::class.java).putExtra("data",it)
                                startActivity(intent)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@MapsSearchActivity, ""+error.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }
}
