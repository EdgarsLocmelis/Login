package com.example.login.core.user.domain.repository

import com.example.login.core.data.DataResult
import com.example.login.core.user.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the User Repository.
 */
interface IUserRepository {
    fun getUserById(userId: Long): Flow<DataResult<User>>
    fun getUserByEmail(email: String): Flow<DataResult<User?>>
    fun addUser(email: String, password: String): Flow<DataResult<User>>
}