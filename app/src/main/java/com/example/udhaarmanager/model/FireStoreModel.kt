package com.example.udhaarmanager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FireStoreModel(
    var title: String? = null,
    var amount: Double? = null,
    var transactionType: String? = null,
    var tag: String? = null,
    var borrowDate: String? = null,
    var returnDate: String? = null,
    var note: String? = null,
    var createdAt: Long? = null,
    val createdAtDateFormat: String? = null,
    val id: Long? = null,
    val number: String? = null
) : Parcelable

@Parcelize
data class ContactModel(
    var name: String? = null,
    val number: String?= null
): Parcelable
