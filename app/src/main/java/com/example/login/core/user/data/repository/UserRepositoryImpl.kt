package com.example.login.core.user.data.repository

import com.example.login.core.data.DataResult
import com.example.login.core.user.data.local.dao.UserDao
import com.example.login.core.user.data.local.entity.UserEntity
import com.example.login.core.user.data.mapper.UserMapper
import com.example.login.core.user.domain.model.User
import com.example.login.core.user.domain.repository.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper
): IUserRepository {

    override fun getUserById(userId: Long): Flow<DataResult<User>> = flow {
        emit(DataResult.Loading)
        try {
            val user = userDao.getUserById(userId)?.let {
                userMapper.mapEntityToDomain(it)
            }
            if (user != null) {
                emit(DataResult.Success(user))
            } else {
                emit(DataResult.Error("User not found"))
            }
        } catch (e: Exception) {
            emit(DataResult.Failed(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)


    override fun getUserByEmail(email: String): Flow<DataResult<User>> = flow {
        emit(DataResult.Loading)
        try {
            val user = userDao.getUserByEmail(email)?.let {
                userMapper.mapEntityToDomain(it)
            }
            if (user != null) {
                emit(DataResult.Success(user))
            } else {
                emit(DataResult.Error("User not found"))
            }
        } catch (e: Exception) {
            emit(DataResult.Failed(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun addUser(email: String, password: String): Flow<DataResult<User>> = flow {
        emit(DataResult.Loading)
        try {
            val userEntity = UserEntity(
                email = email,
                password = password
            )
            val id = userDao.insertUser(userEntity)
            val user = userMapper.mapEntityToDomain(
                UserEntity(
                    id = id,
                    email = email,
                    password = password
                )
            )
            emit(DataResult.Success(user))
        } catch (e: Exception) {
            emit(DataResult.Failed(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}