package com.example.login.core.data

/**
 * Sealed class representing the result of a data operation.
 */
sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val message: String) : DataResult<Nothing>()
    data class Failed(val message: String) : DataResult<Nothing>()
    data object Loading : DataResult<Nothing>()
}