package com.example.udhaarmanager.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.udhaarmanager.model.Transaction
import com.example.udhaarmanager.repo.TransactionRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    application: Application,
    private val transactionRepo: TransactionRepo
) :
    AndroidViewModel(application) {

    //insert transaction
    fun insert(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.insert(transaction)
    }

    //delete transaction
    fun delete(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.delete(transaction)
    }

    //update transaction
    fun update(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.update(transaction)
    }

    //get all transaction
    fun getAllTransaction() = viewModelScope.launch {
        transactionRepo.getAllTransaction()
    }

}