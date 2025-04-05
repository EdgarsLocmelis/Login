package com.example.login.features.auth.domain.validator

import androidx.annotation.StringRes
import com.example.login.R

sealed class GeneralError (@StringRes val messageResId: Int): IValidationError {
    data object UnexpectedError : GeneralError(R.string.unexpected_error)
    data object InvalidCredentials : GeneralError(R.string.invalid_credentials)
}