package com.example.udhaarmanager.repo

import com.example.udhaarmanager.database.ContactDao
import com.example.udhaarmanager.model.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepo @Inject constructor(private val database: ContactDao) {

    suspend fun insert(contact: Contact) {
        database.insertContact(contact)
    }

    val getAllContacts: Flow<List<Contact>> = database.getAllContacts()

    fun searchContact(query: String): Flow<List<Contact>> {
        return database.contactSearch(query)
    }
}