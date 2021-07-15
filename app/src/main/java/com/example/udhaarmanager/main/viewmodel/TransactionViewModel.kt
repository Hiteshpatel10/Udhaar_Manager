package com.example.udhaarmanager.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.udhaarmanager.model.Transaction
import com.example.udhaarmanager.repo.TransactionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    application: Application,
    private val transactionRepo: TransactionRepo
) :
    AndroidViewModel(application) {

    val allTransaction: LiveData<List<Transaction>> = transactionRepo.getAllTransaction.asLiveData()


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


}