package com.example.login.features.auth.domain.validator

/**
 * Interface for validators.
 */
interface IValidator<T> {
    fun validate(input: T): Boolean
    fun getErrorMessage(input: T): IValidationError
}