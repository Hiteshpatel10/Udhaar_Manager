package com.example.udhaarmanager.model

import androidx.room.ColumnInfo

data class TModel(
    var title: String? = null,
    var amount: Long? = null,
    var transactionType: String? = null,
    var tag: String? = null,
    var borrowDate: String? = null,
    var returnDate: String? = null,
    var note: String? = null,
    var createdAt: Long? = null,
    val createdAtDateFormat: String? = null,
    val id: Long? = null
)
