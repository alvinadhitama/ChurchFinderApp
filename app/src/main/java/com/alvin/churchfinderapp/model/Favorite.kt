package com.alvin.churchfinderapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
    var poster: String?="",
    var eng_name: String?="",
    var ind_name: String?="",
    var simple_name: String?="",
    var address: String?="",
    var photo: String?="",
    var display: String?="",
    var language: String?="",
    var contact: String?="",
    var rating: String?="",
    var facility: String?="",
    var schedule: String?=""
): Parcelable