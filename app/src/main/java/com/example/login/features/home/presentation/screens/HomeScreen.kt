package com.example.login.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.example.login.R
import com.example.login.core.presentation.components.LoadingIndicator
import com.example.login.core.presentation.components.MainButton
import com.example.login.core.presentation.components.PreviewWrapper
import com.example.login.core.presentation.utils.UiState
import com.example.login.core.user.domain.model.User
import timber.log.Timber

@Composable
fun HomeScreen(
    userState: UiState<User?>,
    onLogout: () -> Unit
) {
    val data = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(userState) {
        when (userState) {
            is UiState.Success -> {
                userState.data?.let { user ->
                    Timber.d("Entered home with user: ${user.email}")
                    data.value = context.getString(R.string.home_screen_welcome, user.email)
                }
            }
            is UiState.Error -> {
                Timber.e("Entered home with error: ${userState.message}")
                data.value = context.getString(R.string.error_message, userState.message)
            }
            else -> {}
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.value,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                MainButton(
                    text = context.getString(R.string.logout),
                    enabled = true,
                    onClick = onLogout
                )
            }
        }
    }


    LoadingIndicator(visible = userState is UiState.Loading)
}

@PreviewFontScale
@Composable
fun HomeScreenPreview() {
    PreviewWrapper {
        HomeScreen(
            userState = UiState.Success(User(0, "John@doe.test", "Doe")),
            onLogout = {}
        )
    }
}