package com.alvin.churchfinderapp.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvin.churchfinderapp.R
import com.bumptech.glide.Glide

class ViewHolderMapsSearch(var mView: View) : RecyclerView.ViewHolder(mView) {

    fun setDetailsMaps(
        ctx: Context?, name: String?, rating: String?, image: String?
    ) {
        val mName = mView.findViewById<TextView>(R.id.tv_simple_name_maps_search)
        val mRating = mView.findViewById<TextView>(R.id.tv_rate_maps_search)
        val mImage = mView.findViewById<ImageView>(R.id.iv_poster_image_maps_search)
        mName.text = name
        mRating.text = rating
        Glide.with(ctx!!).load(image).into(mImage)
    }

}