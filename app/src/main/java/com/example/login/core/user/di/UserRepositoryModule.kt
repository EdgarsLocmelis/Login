package com.example.login.core.user.di

import com.example.login.core.user.data.repository.UserRepositoryImpl
import com.example.login.core.user.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for binding the User Repository interface to the implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): IUserRepository
}