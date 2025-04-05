package com.example.login.features.auth.presentation.screens.login

import android.content.Context
import com.example.login.core.user.domain.manager.UserManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.core.presentation.utils.IoProcessingUtils
import com.example.login.features.auth.domain.AuthResult
import com.example.login.features.auth.domain.usecase.LoginUserUseCase
import com.example.login.features.auth.domain.usecase.SignUpUserUseCase
import com.example.login.features.auth.domain.validator.GeneralError
import com.example.login.features.auth.domain.validator.email.EmailError
import com.example.login.features.auth.domain.validator.password.PasswordError
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val signUpUserUseCase: SignUpUserUseCase,
    private val userManager: UserManager,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _authFormState = MutableStateFlow(AuthFormState())
    val authFormState = _authFormState.asStateFlow()

    data class AuthFormState(
        val emailError: String? = null,
        val passwordError: String? = null,
        val isSubmitting: Boolean = false,
        val isAuthorized: Boolean = false,
    )

    fun resetAuthState() {
        _authFormState.value = AuthFormState()
    }

    fun login(email: String, password: String) {
        Timber.d("Attempting login for email: $email")
        resetAuthState()
        viewModelScope.launch {
            _authFormState.value = _authFormState.value.copy(
                isSubmitting = true
            )

            IoProcessingUtils.withMinimumProcessingTime {
                loginUserUseCase(email, password)
            }.collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        _authFormState.value = _authFormState.value.copy(
                            isSubmitting = true
                        )
                    }

                    is AuthResult.Success -> {
                        _authFormState.value = _authFormState.value.copy(
                            isAuthorized = true
                        )
                        userManager.setUser(result.data)
                    }

                    is AuthResult.Error -> {
                        when (result.error) {
                            is EmailError -> {
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    emailError = context.getString(result.error.messageResId),
                                )
                            }

                            is PasswordError -> {
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    passwordError = context.getString(result.error.messageResId),
                                )
                            }

                            is GeneralError -> {
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    emailError = "",
                                    passwordError = context.getString(result.error.messageResId)
                                )
                            }

                            else -> {
                                Timber.e("Unexpected error: $result")
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    emailError = "",
                                    passwordError = context.getString(GeneralError.UnexpectedError.messageResId)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        Timber.d("Attempting signup for email: $email")
        resetAuthState()
        viewModelScope.launch {
            _authFormState.value = _authFormState.value.copy(
                isSubmitting = true
            )

            IoProcessingUtils.withMinimumProcessingTime {
                signUpUserUseCase.invoke(
                    email,
                    password
                )
            }.collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        _authFormState.value = _authFormState.value.copy(
                            isSubmitting = true
                        )
                    }

                    is AuthResult.Success -> {
                        _authFormState.value = _authFormState.value.copy(
                            isAuthorized = true
                        )
                        userManager.setUser(result.data)
                    }

                    is AuthResult.Error -> {
                        when (result.error) {
                            is EmailError -> {
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    emailError = context.getString(result.error.messageResId),
                                )
                            }

                            is PasswordError -> {
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    passwordError = context.getString(result.error.messageResId),
                                )
                            }

                            is GeneralError -> {
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    emailError = "",
                                    passwordError = context.getString(result.error.messageResId)
                                )
                            }

                            else -> {
                                Timber.e("Unexpected error: $result")
                                _authFormState.value = _authFormState.value.copy(
                                    isSubmitting = false,
                                    emailError = "",
                                    passwordError = context.getString(GeneralError.UnexpectedError.messageResId)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}