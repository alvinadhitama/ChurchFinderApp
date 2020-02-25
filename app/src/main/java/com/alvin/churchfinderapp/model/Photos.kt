package com.alvin.churchfinderapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photos (
    var name :String?="",
    var url :String?=""
):Parcelable