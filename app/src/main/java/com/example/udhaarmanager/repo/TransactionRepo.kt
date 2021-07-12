package com.example.udhaarmanager.repo

import com.example.udhaarmanager.database.AppDatabase
import com.example.udhaarmanager.database.TransactionDao
import com.example.udhaarmanager.model.Transaction
import javax.inject.Inject

class TransactionRepo @Inject constructor(private val database: TransactionDao) {

    suspend fun insert(transaction: Transaction) {
        database.insertTransaction(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        database.deleteTransaction(transaction)
    }

    suspend fun update(transaction: Transaction) {
        database.updateTransaction(transaction)
    }

    fun getAllTransaction() {
        database.getAllTransaction()
    }

    fun getAllTransactionType(transactionType: String) {
        if (transactionType == "Overall") {
            getAllTransaction()
        } else {
            database.getAllTransactionType(transactionType)
        }
    }
}