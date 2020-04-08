package com.alvin.churchfinderapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedules (
    var time :String?="",
    var day :String?="",
    var masslanguage :String?=""
): Parcelable