package com.example.login.features.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.login.core.presentation.components.PreviewWrapper
import com.example.login.features.auth.presentation.screens.login.AuthScreen
import com.example.login.features.auth.presentation.screens.login.AuthViewModel

@Preview
@Composable
fun BlankAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { },
            signUp = { },
            onSuccess = { }
        )
    }
}

@Preview
@Composable
fun ErrorEmailAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(
                emailError = "Error text"
            ),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { },
            signUp = { },
            onSuccess = { }
        )
    }
}

@Preview
@Composable
fun ErrorPasswordAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(
                passwordError = "Error text"
            ),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { },
            signUp = { },
            onSuccess = { }
        )
    }
}

@Preview
@Composable
fun ErrorUiAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(
                emailError = "",
                passwordError = "Error text"
            ),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { },
            signUp = { },
            onSuccess = { }
        )
    }
}