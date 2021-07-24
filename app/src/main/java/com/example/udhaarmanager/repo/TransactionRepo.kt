package com.example.udhaarmanager.repo

import com.example.udhaarmanager.database.TransactionDao
import com.example.udhaarmanager.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepo @Inject constructor(private val database: TransactionDao) {

    suspend fun insert(transaction: Transaction) {
        database.insertTransaction(transaction)
    }

    suspend fun delete(id: Int) {
        database.deleteTransaction(id)
    }

    suspend fun update(transaction: Transaction) {
        database.updateTransaction(transaction)
    }

    val getAllTransaction: Flow<List<Transaction>> = database.getAllTransaction()


    fun getAllTransactionType(transactionType: String) {
        if (transactionType == "Overall") {
            getAllTransaction
        } else {
            database.getAllTransactionType(transactionType)
        }
    }
}