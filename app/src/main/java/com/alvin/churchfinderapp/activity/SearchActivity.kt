package com.alvin.churchfinderapp.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.adapter.SearchAdapter
import com.alvin.churchfinderapp.adapter.ViewHolderKotlin
import com.alvin.churchfinderapp.adapter.ViewHolder.ClickListener
import com.alvin.churchfinderapp.model.Church
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity() {

    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView

    lateinit var mDatabase : DatabaseReference

    var context: Context? = null

    private var dataList = ArrayList<Church>()

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
                firebaseSearch(searchText)
            }
        } )

    }

    override fun onStart() {
        super.onStart()
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Church, ViewHolderKotlin> =
            object : FirebaseRecyclerAdapter<Church, ViewHolderKotlin>(
                Church::class.java,
                R.layout.row_item_all,
                ViewHolderKotlin::class.java,
                mDatabase
            ) {
                override fun populateViewHolder(viewHolder: ViewHolderKotlin, church: Church, position: Int
                ) {
                    viewHolder.setDetails(applicationContext, church.eng_name, church.rating, church.display)

                    viewHolder.itemView.setOnClickListener {
//                        val intent = Intent(context, DetailActivity::class.java).putExtra("data",model?.simple_name)
//                        startActivity(intent)
                        Toast.makeText(this@SearchActivity,church.simple_name,Toast.LENGTH_LONG).show()
                    }

                    mDatabase.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataList.clear()
                            for (getdataSnapshot in dataSnapshot.getChildren()) {
                                val church = getdataSnapshot.getValue(Church::class.java!!)
                                dataList.add(church!!)
                            }

                            rv_list_search.adapter = SearchAdapter(dataList){
                                val intent = Intent(this@SearchActivity, DetailActivity::class.java).putExtra("data",it)
                                startActivity(intent)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@SearchActivity, ""+error.message, Toast.LENGTH_LONG).show()
                        }
                    })


                }
            }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }

    private fun firebaseSearch(searchText: String) {
        val firebaseSearchQuery: Query =
            mDatabase.orderByChild("simple_name").startAt(searchText).endAt("$searchText\uf88ff")
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Church, ViewHolderKotlin> =
            object : FirebaseRecyclerAdapter<Church, ViewHolderKotlin>(
                Church::class.java,
                R.layout.row_item_all,
                ViewHolderKotlin::class.java,
                firebaseSearchQuery
            ) {
                override fun populateViewHolder(viewHolder: ViewHolderKotlin, church: Church, i: Int) {
                    viewHolder.setDetails(applicationContext, church.eng_name, church.rating, church.display)

                    viewHolder.itemView.setOnClickListener {
//                        val intent = Intent(context, DetailActivity::class.java).putExtra("data",model?.simple_name)
//                        startActivity(intent)
                        Toast.makeText(this@SearchActivity,church.simple_name,Toast.LENGTH_LONG).show()
                    }

                    firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataList.clear()
                            for (getdataSnapshot in dataSnapshot.getChildren()) {
                                val church = getdataSnapshot.getValue(Church::class.java!!)
                                dataList.add(church!!)
                            }

                            rv_list_search.adapter = SearchAdapter(dataList){
                                val intent = Intent(this@SearchActivity, DetailActivity::class.java).putExtra("data",it)
                                startActivity(intent)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@SearchActivity, ""+error.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }

            }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }




/////////////////////////////////////////////////////////



//    private fun loadFirebaseData(searchText : String) {
//
//        if(searchText.isEmpty()){
//
//            FirebaseRecyclerAdapter.cleanup()
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }else {
//            val firebaseSearchQuery = mDatabase.orderByChild("simple_name").startAt(searchText).endAt(searchText + "\uf8ff")
//
//            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Church, UsersViewHolder>(
//
//                Church::class.java,
//                R.layout.row_item_all,
//                UsersViewHolder::class.java,
//                firebaseSearchQuery
//
//            ) {
//                override fun populateViewHolder(viewHolder: UsersViewHolder, model: Church?, position: Int) {
//
//                    viewHolder.mview.tv_simple_name_all.setText(model?.eng_name)
//                    viewHolder.mview.tv_rate_all.setText(model?.rating)
//                    //Picasso.with(applicationContext).load(model?.image).into(viewHolder.mview.UserImageView)
//                    Glide.with(applicationContext)
//                        .load(model?.display)
//                        .into(viewHolder.mview.iv_poster_image_all)
//
////                    viewHolder.itemView.setOnClickListener {
////                        val intent = Intent(this@SearchActivity, DetailActivity::class.java)
////                        intent.putExtra(model?.simple_name,"simple_name")
////                        startActivity(intent)
////                    }
////                    fun bindItem(data: Church, listener: (Church) -> Unit, context : Context, position : Int) {
////
////                        viewHolder.itemView.setOnClickListener {
////                            listener(data)
////                        }
////                    }
////
////                    viewHolder.itemView.setOnClickListener {
////                        val intent = Intent(context, DetailActivity::class.java).putExtra("data",model?.simple_name)
////                        startActivity(intent)
////                        Toast.makeText(this@SearchActivity,model?.simple_name,Toast.LENGTH_LONG).show()
////                    }
//
////                    holder.full_name.setOnClickListener(View.OnClickListener {
////                        Toast.makeText(context,
////                            "Full Name Clicked", Toast.LENGTH_SHORT).show()
////                    })
//
//                }
//
//            }
//
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }
//    }


    // // View Holder Class

    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {

    }
}
