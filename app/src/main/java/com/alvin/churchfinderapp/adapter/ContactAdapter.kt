package com.alvin.churchfinderapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.Contacts
import com.alvin.churchfinderapp.model.Schedules
import com.bumptech.glide.Glide

class ContactAdapter (private var data: List<Contacts>,
                       private val listener: (Contacts)-> Unit)
    :RecyclerView.Adapter<ContactAdapter.LeagueViewHolder>(){
    lateinit var ContextAdapter :Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_contact, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val tvImage: ImageView = view.findViewById(R.id.contact_type)
        //private val tvContactName: TextView = view.findViewById(R.id.contact_name)
        private val tvContactData: TextView = view.findViewById(R.id.contact_data)

        fun bindItem(data: Contacts, listener: (Contacts) -> Unit, context : Context, position : Int) {

            //tvContactName.text = data.contact_type
            tvContactData.text = data.contact_data

            Glide.with(context)
                .load(data.contact_type)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}