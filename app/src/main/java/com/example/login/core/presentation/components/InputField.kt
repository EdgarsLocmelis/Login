package com.example.login.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            visualTransformation = visualTransformation,
            isError = errorMessage != null,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,

            ),
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = MaterialTheme.shapes.small
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 24.dp)
        ) {
                AnimatedVisibility(
                    visible = errorMessage != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp)
                    )
                }

        }
    }
}

@PreviewFontScale
@Composable
fun InputFieldPreview() {
    PreviewWrapper {
        InputField(
            value = "",
            onValueChange = {},
            label = "Label",
            enabled = true,
            errorMessage = null,
            visualTransformation = VisualTransformation.None
        )
    }
}

@PreviewFontScale
@Composable
fun InputFieldErrorPreview() {
    PreviewWrapper {
        InputField(
            value = "",
            onValueChange = {},
            label = "Label",
            enabled = true,
            errorMessage = "Error message",
            visualTransformation = VisualTransformation.None
        )
    }
}