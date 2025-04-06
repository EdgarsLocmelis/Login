package com.example.login.features.auth.di

import com.example.login.features.auth.domain.validator.IValidator
import com.example.login.features.auth.domain.validator.email.EmailValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing the Email Validator.
 */
@Module
@InstallIn(SingletonComponent::class)
object EmailValidatorModule {

    @Provides
    @Singleton
    fun provideEmailValidator(): EmailValidator {
        return EmailValidator()
    }

    @Provides
    @Singleton
    fun provideEmailValidatorInterface(emailValidator: EmailValidator): IValidator<String> {
        return emailValidator
    }
}