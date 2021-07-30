package com.example.udhaarmanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.udhaarmanager.model.Contact
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactDao {

    //to insert new transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contact: Contact)

    //to get transactions list
    @Query("SELECT * FROM contact_list ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>

    // to get transaction based on transactionType
    @Query("SELECT * FROM contact_list WHERE  name LIKE :query OR number LIKE :query ORDER BY name ASC")
    fun contactSearch(query: String): Flow<List<Contact>>

}