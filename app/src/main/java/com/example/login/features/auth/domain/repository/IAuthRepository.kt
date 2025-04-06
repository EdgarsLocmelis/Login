package com.example.login.features.auth.domain.repository

/**
 * Interface for the Authentication Repository.
 */
interface IAuthRepository {
    suspend fun getAuthorisedUserId(): Long?
    suspend fun saveAuthorisedUserId(userId: Long)
    suspend fun resetAuthorisedId()
}