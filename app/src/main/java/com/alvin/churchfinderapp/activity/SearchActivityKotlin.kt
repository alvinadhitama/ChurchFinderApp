package com.alvin.churchfinderapp.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.SearchAdapter
import com.alvin.churchfinderapp.model.Church
import com.alvin.churchfinderapp.utils.Preferences
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_church.iv_back
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.row_item_all.view.*
import java.util.*

class SearchActivityKotlin : AppCompatActivity() {

    var toolbar: androidx.appcompat.widget.Toolbar? = null
    lateinit var mSearchText : EditText

    private lateinit var preference: Preferences
    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Church>()

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<Church, UsersViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mSearchText = findViewById(R.id.search_text)
        preference = Preferences(this)
        mDatabase = FirebaseDatabase.getInstance().getReference("Church")


        rv_list_search.layoutManager = LinearLayoutManager(this)
        getData()

        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search,menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_data)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(this@SearchActivityKotlin,"$query",Toast.LENGTH_SHORT).show()

                loadData("$query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })
        return true
    }

    private fun loadData(searchText: String) {
        val firebaseSearchQuery = mDatabase.orderByChild("simple_name").startAt(searchText).endAt(searchText + "\uf8ff")
        FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Church, UsersViewHolder>(

            Church::class.java,
            R.layout.row_item_all,
            UsersViewHolder::class.java,
            firebaseSearchQuery

        ){
            override fun populateViewHolder(viewHolder: UsersViewHolder, model: Church?, position: Int) {
                viewHolder.mview.tv_simple_name_all.setText(model?.eng_name)

                Glide.with(applicationContext)
                    .load(model?.display)
                    .into(viewHolder.mview.iv_poster_image_all)

                firebaseSearchQuery.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataList.clear()
                        for (getdataSnapshot in dataSnapshot.getChildren()) {
                            val church = getdataSnapshot.getValue(Church::class.java!!)
                            dataList.add(church!!)
                        }

                        rv_list_search.adapter = SearchAdapter(dataList){
                            val intent = Intent(this@SearchActivityKotlin, DetailActivity::class.java).putExtra("data",it)
                            startActivity(intent)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@SearchActivityKotlin, ""+error.message, Toast.LENGTH_LONG).show()
                    }
                })

            }

        }
        rv_list_search.adapter = FirebaseRecyclerAdapter
    }

    private fun getData2(searchText: String) {
        mDatabase.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val church = getdataSnapshot.getValue(Church::class.java!!)
                    dataList.add(church!!)
                }

                rv_list_search.adapter = SearchAdapter(dataList){
                    val intent = Intent(this@SearchActivityKotlin, DetailActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchActivityKotlin, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getData() {
        mDatabase.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val church = getdataSnapshot.getValue(Church::class.java!!)
                    dataList.add(church!!)
                }

                rv_list_search.adapter = SearchAdapter(dataList){
                    val intent = Intent(this@SearchActivityKotlin, DetailActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchActivityKotlin, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {

    }



}