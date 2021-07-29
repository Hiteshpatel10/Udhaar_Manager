package com.example.udhaarmanager.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "transactions")
data class Transaction(
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "amount")
    var amount: Double,
    @ColumnInfo(name = "transactionType")
    var transactionType: String,
    @ColumnInfo(name = "tag")
    var tag: String?,
    @ColumnInfo(name = "borrowDate")
    var borrowDate: String,
    @ColumnInfo(name = "returnDate")
    var returnDate: String,
    @ColumnInfo(name = "note")
    var note: String,
    @ColumnInfo(name = "createdAt")
    var createdAt: Long,
    var number: String,

) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    val createdAtDateFormat: String
    get() = DateFormat.getDateTimeInstance()
        .format(createdAt) // Date Format: Jan 11, 2021, 11:30 AM
}

