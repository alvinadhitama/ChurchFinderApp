package com.alvin.churchfinderapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.Church
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.row_item_all.view.*

class SearchActivity : AppCompatActivity() {

    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView

    lateinit var mDatabase : DatabaseReference

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<Church, UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mSearchText =findViewById(R.id.search_text)
        mRecyclerView = findViewById(R.id.rv_list_search)
        iv_back.setOnClickListener {
            finish()
        }


        mDatabase = FirebaseDatabase.getInstance().getReference("Church")


        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))




        mSearchText.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = mSearchText.getText().toString().trim()

                loadFirebaseData(searchText)
            }
        } )

    }

    private fun loadFirebaseData(searchText : String) {

        if(searchText.isEmpty()){

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }else {


            val firebaseSearchQuery = mDatabase.orderByChild("simple_name").startAt(searchText).endAt(searchText + "\uf8ff")

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Church, UsersViewHolder>(

                Church::class.java,
                R.layout.row_item_all,
                UsersViewHolder::class.java,
                firebaseSearchQuery


            ) {
                override fun populateViewHolder(viewHolder: UsersViewHolder, model: Church?, position: Int) {


                    viewHolder.mview.tv_simple_name_all.setText(model?.eng_name)
                    viewHolder.mview.tv_rate_all.setText(model?.rating)
                    //Picasso.with(applicationContext).load(model?.image).into(viewHolder.mview.UserImageView)
                    Glide.with(applicationContext)
                        .load(model?.display)
                        .into(viewHolder.mview.iv_poster_image_all)

                }

            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }


    // // View Holder Class

    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {

    }
}
