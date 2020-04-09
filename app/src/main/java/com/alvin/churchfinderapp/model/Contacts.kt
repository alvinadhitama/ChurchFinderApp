package com.alvin.churchfinderapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contacts (
    var contact_type :String?="",
    var contact_data :String?=""
): Parcelable