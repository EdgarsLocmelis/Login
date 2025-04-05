package com.example.login.features.auth.domain

import com.example.login.features.auth.domain.validator.IValidationError

sealed class AuthResult<out T> {
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val error: IValidationError) :
        AuthResult<Nothing>()
    data object Loading : AuthResult<Nothing>()
}