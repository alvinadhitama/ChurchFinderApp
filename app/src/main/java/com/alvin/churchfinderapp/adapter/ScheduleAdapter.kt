package com.alvin.churchfinderapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.alvin.churchfinderapp.model.Schedules

class ScheduleAdapter (private var data: List<Schedules>,
                        private val listener: (Schedules)-> Unit)
    :RecyclerView.Adapter<ScheduleAdapter.LeagueViewHolder>(){
    lateinit var ContextAdapter :Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_schedule, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View): RecyclerView.ViewHolder(view){

        //private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)
        private val tvTime: TextView = view.findViewById(R.id.schedule_time)
        private val tvDay: TextView = view.findViewById(R.id.schedule_day)
        private val tvLanguage: TextView = view.findViewById(R.id.schedule_language)

        fun bindItem(data: Schedules, listener: (Schedules) -> Unit, context : Context, position : Int) {

            tvTime.text = data.time
            tvDay.text = data.day
            tvLanguage.text = data.masslanguage

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }
}