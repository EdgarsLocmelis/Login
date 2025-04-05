package com.example.login.features.auth.presentation.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import com.example.login.R
import com.example.login.core.presentation.components.InputField
import com.example.login.core.presentation.components.LoadingIndicator
import com.example.login.core.presentation.components.MainButton
import com.example.login.core.presentation.components.PreviewWrapper
import timber.log.Timber

@Composable
fun AuthScreen(
    authFormState: AuthViewModel.AuthFormState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    login: (String, String) -> Unit,
    signUp: (String, String) -> Unit,
    onSuccess: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Ignore back press on auth screen
    BackHandler(enabled = true) {
        // Do nothing, effectively ignoring the back press
    }

    LaunchedEffect(authFormState) {
        if (authFormState.isAuthorized) {
            Timber.d("Login/Signup successful, navigating to home")
            onSuccess("")
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(140.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = context.getString(R.string.l_authentication),
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(32.dp))

                InputField(
                    value = email,
                    onValueChange = {
                        email = it
                        onEmailChanged(email)
                    },
                    label = context.getString(R.string.l_email),
                    enabled = !authFormState.isSubmitting,
                    errorMessage = authFormState.emailError,
                    visualTransformation = VisualTransformation.None
                )

                InputField(
                    value = password,
                    onValueChange = {
                        password = it
                        onPasswordChanged(password)
                    },
                    label = context.getString(R.string.password),
                    enabled = !authFormState.isSubmitting,
                    errorMessage = authFormState.passwordError,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                MainButton(
                    text = context.getString(R.string.login),
                    enabled = !authFormState.isSubmitting,
                    onClick = { login(email, password) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = context.getString(R.string.or),
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MainButton(
                    text = context.getString(R.string.register),
                    enabled = !authFormState.isSubmitting,
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = { signUp(email, password) }
                )
            }

            LoadingIndicator(visible = authFormState.isSubmitting)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BlankAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { _, _ -> },
            signUp = { _, _ -> },
            onSuccess = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorUiAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { _, _ -> },
            signUp = { _, _ -> },
            onSuccess = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorEmailAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(
                emailError = "Error text"
            ),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { _, _ -> },
            signUp = { _, _ -> },
            onSuccess = {}
        )
    }
}

@PreviewFontScale
@Composable
fun ErrorPasswordAuthScreenPreview() {
    PreviewWrapper {
        AuthScreen(
            authFormState = AuthViewModel.AuthFormState(
                passwordError = "Error text"
            ),
            onEmailChanged = {},
            onPasswordChanged = {},
            login = { _, _ -> },
            signUp = { _, _ -> },
            onSuccess = {}
        )
    }
}