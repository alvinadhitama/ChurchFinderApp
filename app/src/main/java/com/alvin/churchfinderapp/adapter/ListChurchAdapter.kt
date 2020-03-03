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

class ListChurchAdapter(private var data: List<Church>,
                        private val listener: (Church) -> Unit)
    : RecyclerView.Adapter<ListChurchAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_all, parent, false)

        return LeagueViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tv_simple_name_all)
        private val tvRate: TextView = view.findViewById(R.id.tv_rate_all)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image_all)

        fun bindItem(data: Church, listener: (Church) -> Unit, context : Context, position : Int) {

            tvTitle.text = data.eng_name
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