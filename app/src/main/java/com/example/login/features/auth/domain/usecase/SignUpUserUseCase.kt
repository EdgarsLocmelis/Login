package com.example.login.features.auth.domain.usecase

import com.example.login.core.data.DataResult
import com.example.login.features.auth.domain.AuthResult
import com.example.login.core.user.domain.model.User
import com.example.login.core.user.domain.repository.IUserRepository
import com.example.login.features.auth.domain.repository.IAuthRepository
import com.example.login.features.auth.domain.validator.GeneralError
import com.example.login.features.auth.domain.validator.email.EmailError
import com.example.login.features.auth.domain.validator.email.EmailValidator
import com.example.login.features.auth.domain.validator.password.PasswordValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val authRepository: IAuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading)

        try {
            if (!emailValidator.validate(email)) {
                emit(AuthResult.Error(
                    error = emailValidator.getErrorMessage(email)
                ))
                return@flow
            }

            if (!passwordValidator.validate(password)) {
                emit(AuthResult.Error(
                    error = passwordValidator.getErrorMessage(password)
                ))
                return@flow
            }

            var canProceed = true
            userRepository.getUserByEmail(email).collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        if (result.data != null) {
                            canProceed = false
                            emit(AuthResult.Error(EmailError.EmailAlreadyInUse))
                        }
                    }
                    is DataResult.Error -> {
                        canProceed = true
                    }
                    is DataResult.Loading -> { /* Ignore loading state */ }
                    is DataResult.Failed -> {
                        canProceed = false
                        Timber.e("Signup failed on validating if user is already present: ${result.message}")
                        emit(AuthResult.Error(GeneralError.UnexpectedError))
                    }
                }
            }

            if (!canProceed) {
                return@flow
            }

            userRepository.addUser(email, password).collect { result ->
                when(result) {
                    is DataResult.Error -> {
                        Timber.e("Signup error: ${result.message}")
                        emit(AuthResult.Error(GeneralError.UnexpectedError))
                    }
                    is DataResult.Failed -> {
                        Timber.e("Signup failed: ${result.message}")
                        emit(AuthResult.Error(GeneralError.UnexpectedError))
                    }
                    DataResult.Loading -> { /* Ignore loading state */ }
                    is DataResult.Success -> {
                        Timber.d("Signup successful: user: ${result.data.email}")
                        emit(AuthResult.Success(result.data))
                    }
                }

                if (result is DataResult.Success) {
                    authRepository.saveAuthorisedUserId(result.data.id)
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Signup failed with exception")
            emit(AuthResult.Error(GeneralError.UnexpectedError))
        }
    }
}