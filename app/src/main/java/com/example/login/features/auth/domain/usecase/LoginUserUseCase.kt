package com.example.login.features.auth.domain.usecase

import com.example.login.core.data.DataResult
import com.example.login.features.auth.domain.AuthResult
import com.example.login.core.user.domain.model.User
import com.example.login.core.user.domain.repository.IUserRepository
import com.example.login.features.auth.domain.repository.IAuthRepository
import com.example.login.features.auth.domain.validator.GeneralError
import com.example.login.features.auth.domain.validator.email.EmailError
import com.example.login.features.auth.domain.validator.email.EmailValidator
import com.example.login.features.auth.domain.validator.password.PasswordError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val authRepository: IAuthRepository,
    private val emailValidator: EmailValidator
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading)

        try {
            if (!emailValidator.validate(email)) {
                Timber.w("Login failed: Invalid email format")
                emit(
                    AuthResult.Error(
                    error = emailValidator.getErrorMessage(email)
                ))
                return@flow
            }

            if (password.isEmpty()) {
                Timber.w("Login failed: Empty password")
                emit(
                    AuthResult.Error(
                        error = PasswordError.EmptyPassword
                    )
                )
                return@flow
            }

            userRepository.getUserByEmail(email).collect { result ->
                when (result) {
                    is DataResult.Loading -> {
                        emit(AuthResult.Loading)
                    }

                    is DataResult.Success -> {
                        if (result.data?.password == password) {
                            Timber.d("Login successful: user: ${result.data.email}")
                            authRepository.saveAuthorisedUserId(result.data.id)
                            emit(AuthResult.Success(result.data))
                        } else {
                            Timber.w("Login failed: Invalid password for email: $email")
                            emit(AuthResult.Error(
                                error = GeneralError.InvalidCredentials,
                            ))
                        }
                    }

                    is DataResult.Error -> {
                        Timber.d("Login error - no user: ${result.message}")
                        emit(AuthResult.Error(
                            error = EmailError.AccountNotFound
                        ))
                    }

                    is DataResult.Failed -> {
                        Timber.e("Login error - failed: ${result.message}")
                        emit(AuthResult.Error(GeneralError.UnexpectedError))
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Login failed with exception")
            emit(AuthResult.Error(GeneralError.UnexpectedError))
        }
    }
}
