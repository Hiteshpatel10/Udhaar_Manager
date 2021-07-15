package com.example.udhaarmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.udhaarmanager.model.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {

    //to insert new transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    //to delete a transaction
    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    //to update an existing transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    //to get transactions list
    @Query("SELECT * FROM transactions ORDER BY createdAt DESC")
    fun getAllTransaction(): Flow<List<Transaction>>

    // to get transaction based on transactionType
    @Query("SELECT * FROM transactions WHERE transactionType == :transactionType ORDER BY createdAt DESC")
    fun getAllTransactionType(transactionType: String): Flow<List<Transaction>>

}