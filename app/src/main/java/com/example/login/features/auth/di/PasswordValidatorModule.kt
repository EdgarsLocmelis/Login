package com.example.login.features.auth.di

import com.example.login.features.auth.domain.validator.IValidator
import com.example.login.features.auth.domain.validator.password.PasswordValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PasswordValidatorModule {

    @Provides
    @Singleton
    fun providePasswordValidator(): PasswordValidator {
        return PasswordValidator()
    }

    @Provides
    @Singleton
    fun providePasswordValidatorInterface(passwordValidator: PasswordValidator): IValidator<String> {
        return passwordValidator
    }
}