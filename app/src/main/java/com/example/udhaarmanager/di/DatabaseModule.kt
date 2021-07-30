package com.example.udhaarmanager.di

import android.content.Context
import androidx.room.Room
import com.example.udhaarmanager.database.AppDatabase
import com.example.udhaarmanager.database.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "transaction.db"
        ).build()
    }

    @Provides
    fun transactionDao(database: AppDatabase): ContactDao {
        return database.getContactDao()
    }
}