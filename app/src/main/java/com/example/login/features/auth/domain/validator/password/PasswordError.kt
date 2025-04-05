package com.example.login.features.auth.domain.validator.password

import androidx.annotation.StringRes
import com.example.login.R
import com.example.login.features.auth.domain.validator.IValidationError

sealed class PasswordError (@StringRes val messageResId: Int): IValidationError {
    data object EmptyPassword : PasswordError(R.string.empty_password)
    data object PasswordTooShort : PasswordError(R.string.password_too_short)
    data object PasswordTooWeak : PasswordError(R.string.password_too_weak)
    data object WrongPassword : PasswordError(R.string.invalid_credentials)
}

