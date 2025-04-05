package com.example.login.features.auth.domain.usecase

import com.example.login.core.data.DataResult
import com.example.login.core.user.domain.model.User
import com.example.login.core.user.domain.repository.IUserRepository
import com.example.login.features.auth.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAuthorizedUserUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val authRepository: IAuthRepository
) {
    suspend operator fun invoke(): Flow<DataResult<User?>>  {
        return authRepository.getAuthorisedUserId()?.let {
            userRepository.getUserById(it)
        } ?: flowOf(DataResult.Success(null))
    }
}