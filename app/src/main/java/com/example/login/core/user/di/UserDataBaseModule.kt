package com.example.login.core.user.di

import com.example.login.core.user.data.local.database.AppDatabase
import com.example.login.core.user.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing the User Database.
 */
@Module
@InstallIn(SingletonComponent::class)
object UserDataBaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}