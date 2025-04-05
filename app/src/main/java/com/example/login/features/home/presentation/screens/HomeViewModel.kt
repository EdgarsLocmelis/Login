package com.example.login.features.home.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.core.presentation.utils.IoProcessingUtils
import com.example.login.core.presentation.utils.UiState
import com.example.login.core.user.domain.manager.UserManager
import com.example.login.core.user.domain.model.User
import com.example.login.features.home.domain.usecase.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
    private val userManager: UserManager,
) : ViewModel() {

    private val _userState = MutableStateFlow<UiState<User?>>(UiState.Loading)
    val userState: StateFlow<UiState<User?>> = _userState

    init {
        viewModelScope.launch {
            userManager.currentUser
                .collect { user ->
                    _userState.value = if (user != null) {
                        UiState.Success(user)
                    } else {
                        UiState.Error("No user")
                    }
                }
        }
    }

    fun logout() = viewModelScope.launch {
        Timber.d("Logging out user")
        _userState.value = UiState.Loading
        IoProcessingUtils.withMinimumProcessingTime {
            logoutUserUseCase.invoke()
        }.let {
            userManager.setUser(null)
        }
    }
}