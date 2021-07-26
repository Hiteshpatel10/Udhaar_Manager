package com.example.udhaarmanager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactModel(
    var name: String? = null,
    var number: String? = null
): Parcelable
