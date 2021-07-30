package com.example.udhaarmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.udhaarmanager.model.Contact


@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

}