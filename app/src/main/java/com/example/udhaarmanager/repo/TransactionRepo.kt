package com.example.udhaarmanager.repo

import com.example.udhaarmanager.database.AppDatabase
import com.example.udhaarmanager.model.Transaction
import javax.inject.Inject

class TransactionRepo @Inject constructor(private val database: AppDatabase) {

    suspend fun insert(transaction: Transaction) {
        database.getTransactionDao().insertTransaction(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        database.getTransactionDao().deleteTransaction(transaction)
    }

    suspend fun update(transaction: Transaction) {
        database.getTransactionDao().updateTransaction(transaction)
    }

    fun getAllTransaction() {
        database.getTransactionDao().getAllTransaction()
    }

    fun getAllTransactionType(transactionType: String) {
        if (transactionType == "Overall") {
            getAllTransaction()
        } else {
            database.getTransactionDao().getAllTransactionType(transactionType)
        }
    }
}