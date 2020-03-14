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
import com.alvin.churchfinderapp.model.Popular
import com.bumptech.glide.Glide

class PopularAdapter(private var data: List<Popular>,
                        private val listener: (Popular) -> Unit)
    : RecyclerView.Adapter<PopularAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_popular, parent, false)

        return LeagueViewHolder(
            inflatedView
        )
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tv_simple_name_popular)
        private val tvRate: TextView = view.findViewById(R.id.tv_rate_popular)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image_popular)

        fun bindItem(data: Popular, listener: (Popular) -> Unit, context : Context, position : Int) {

            tvTitle.text = data.simple_name
            tvRate.text = data.rating

            Glide.with(context)
                .load(data.display)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}