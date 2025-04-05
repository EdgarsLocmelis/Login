package com.example.login.features.auth.domain.validator

interface IValidator<T> {
    fun validate(input: T): Boolean
    fun getErrorMessage(input: T): IValidationError
}