package com.alvin.churchfinderapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.Church
import com.bumptech.glide.Glide

class ViewHolderKotlin(var mView: View) : RecyclerView.ViewHolder(mView) {

    fun setDetails(
        ctx: Context?, name: String?, rating: String?, image: String?
    ) {
        val mName = mView.findViewById<TextView>(R.id.tv_simple_name_all)
        val mRating = mView.findViewById<TextView>(R.id.tv_rate_all)
        val mImage = mView.findViewById<ImageView>(R.id.iv_poster_image_all)
        mName.text = name
        mRating.text = rating
        Glide.with(ctx!!).load(image).into(mImage)
    }

}

//class ViewHolderKotlin(private var data: List<Church>,
//                     private val listener: (Church) -> Unit)
//    : RecyclerView.Adapter<ViewHolderKotlin.LeagueViewHolder>() {
//
//    lateinit var ContextAdapter : Context
//    var limit = 10
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        ContextAdapter = parent.context
//        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_all, parent, false)
//
//        return LeagueViewHolder(
//            inflatedView
//        )
//    }
//
//    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
//        holder.bindItem(data[position], listener, ContextAdapter, position)
//    }
//
//    override fun getItemCount(): Int {
//        if (data.size > limit)
//            return limit
//        else
//            return data.size
//    }
//
//    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        private val tvTitle: TextView = view.findViewById(R.id.tv_simple_name_all)
//        private val tvRate: TextView = view.findViewById(R.id.tv_rate_all)
//        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image_all)
//
//        fun bindItem(data: Church, listener: (Church) -> Unit, context : Context, position : Int) {
//
//            tvTitle.text = data.eng_name
//            tvRate.text = data.rating
//
//            Glide.with(context)
//                .load(data.display)
//                .into(tvImage)
//
//            itemView.setOnClickListener {
//                listener(data)
//            }
//        }
//    }
//}

