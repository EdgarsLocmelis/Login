package com.example.login.features.auth.domain.validator.email

import androidx.annotation.StringRes
import com.example.login.R
import com.example.login.features.auth.domain.validator.IValidationError

sealed class EmailError (@StringRes val messageResId: Int): IValidationError {
    data object EmptyEmail : EmailError(R.string.empty_email)
    data object InvalidEmailFormat : EmailError(R.string.invalid_email)
    data object AccountNotFound : EmailError(R.string.err_account_not_found)
    data object EmailAlreadyInUse : EmailError(R.string.already_registered)
}