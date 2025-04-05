package com.example.login.features.auth.presentation.screens.splash

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.login.core.presentation.components.LoadingIndicator
import com.example.login.core.presentation.components.PreviewWrapper
import com.example.login.core.presentation.utils.UiState
import com.example.login.core.user.domain.model.User
import timber.log.Timber

@Composable
fun SplashScreen(
    userUiState: UiState<User?>,
    onNeedAuth: () -> Unit,
    onAuthenticated: (User) -> Unit,
    checkAuthState: () -> Unit,
) {
    LaunchedEffect(userUiState) {
        Timber.d("Current auth state: $userUiState")
        when (userUiState) {
            is UiState.Success -> {
                when (userUiState.data) {
                    is User -> {
                        Timber.d("Authenticated, navigating to home")
                        onAuthenticated(userUiState.data)
                    }
                    null -> {
                        Timber.d("Initial state, checking auth")
                        checkAuthState()
                    }
                    else -> {
                        Timber.d("Unknown auth state, navigating to auth")
                        onNeedAuth()
                    }
                }
            }
            is UiState.Loading -> {
                Timber.d("Loading auth state")
            }
            is UiState.Error -> {
                Timber.d("Error: ${userUiState.message}, navigating to auth")
                onNeedAuth()
            }
        }
    }

    Scaffold { padding ->
        LoadingIndicator(
            modifier = Modifier.padding(padding),
            true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    PreviewWrapper {
        SplashScreen(
            userUiState = UiState.Success(null),
            onNeedAuth = {},
            onAuthenticated = {},
            checkAuthState = {}
        )
    }
}