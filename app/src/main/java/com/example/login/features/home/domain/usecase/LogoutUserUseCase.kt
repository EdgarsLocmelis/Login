package com.example.login.features.home.domain.usecase

import com.example.login.core.user.domain.repository.IUserRepository
import com.example.login.features.auth.domain.repository.IAuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
) {
    suspend operator fun invoke() {
        return authRepository.resetAuthorisedId()
    }
}