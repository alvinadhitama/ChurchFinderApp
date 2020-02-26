package com.alvin.churchfinderapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.Photos
import com.bumptech.glide.Glide

class PhotosAdapter(private var data: List<Photos>,
                    private val listener: (Photos) -> Unit)
    : RecyclerView.Adapter<PhotosAdapter.LeagueViewHolder>(){
    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_photos, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View): RecyclerView.ViewHolder(view){
        //private val tvTitle: TextView = view.findViewById(R.id.tv_simple_name)

        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Photos, listener: (Photos) -> Unit, context : Context, position : Int) {
            Glide.with(context)
                .load(data.url)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}