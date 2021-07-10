package com.example.udhaarmanager.di

import android.app.Application
import com.example.udhaarmanager.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.invoke(application.applicationContext)
    }
}