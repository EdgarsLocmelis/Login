package com.example.login.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.login.core.presentation.theme.LoginTheme

/**
 * Preview wrapper to provide the theme and context to the content.
 */
@Composable
fun PreviewWrapper(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    CompositionLocalProvider(
        LocalContext provides context
    ) {
        LoginTheme {
            content()
        }
    }
}