package com.example.udhaarmanager.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contact_list")
data class Contact(
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "number")
    var number: String
) : Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id = number
}
