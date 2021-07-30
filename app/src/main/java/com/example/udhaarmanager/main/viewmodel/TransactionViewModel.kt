package com.example.udhaarmanager.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.udhaarmanager.model.Contact
import com.example.udhaarmanager.repo.TransactionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    application: Application,
    private val transactionRepo: TransactionRepo
) :
    AndroidViewModel(application) {

    val allContacts = transactionRepo.getAllContacts.asLiveData()

    //insert transaction
    fun insert(contact: Contact) = viewModelScope.launch {
        transactionRepo.insert(contact)
    }

    fun searchContact(query: String): LiveData<List<Contact>> {
        return transactionRepo.searchContact(query).asLiveData()
    }

}