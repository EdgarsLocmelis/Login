package com.example.login.features.auth.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.core.data.DataResult
import com.example.login.core.presentation.utils.IoProcessingUtils
import com.example.login.core.presentation.utils.UiState
import com.example.login.core.user.domain.manager.UserManager
import com.example.login.core.user.domain.model.User
import com.example.login.features.auth.domain.usecase.GetAuthorizedUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthorizedUserUseCase: GetAuthorizedUserUseCase,
    private val userManager: UserManager,
) : ViewModel() {

    private val _userUiState = MutableStateFlow<UiState<User?>>(UiState.Success(null))
    val userUiState = _userUiState.asStateFlow()

    fun checkAuthState() {
        Timber.d("Checking authentication state")

        viewModelScope.launch {
            IoProcessingUtils.withMinimumProcessingTime {
                200.milliseconds
                getAuthorizedUserUseCase.invoke() }
                .collect { result ->
                    when (result) {
                        is DataResult.Loading -> {
                            Timber.d("Get auth user in progress")
                            _userUiState.value = UiState.Loading
                        }

                        is DataResult.Success -> {
                            if (result.data == null) {
                                userManager.setUser(null)
                                _userUiState.value = UiState.Error("Not logged in")
                            } else {
                                Timber.d("User authenticated: ${result.data?.email}")
                                userManager.setUser(result.data)
                                _userUiState.value = UiState.Success(result.data)
                            }
                        }

                        is DataResult.Error -> {
                            Timber.e("Error: ${result.message}")
                            userManager.setUser(null)
                            _userUiState.value = UiState.Error("No user")
                        }

                        is DataResult.Failed -> {
                            Timber.e("Failed: ${result.message}")
                            userManager.setUser(null)
                            _userUiState.value = UiState.Error("No user")
                        }
                    }
                }
        }
    }
}