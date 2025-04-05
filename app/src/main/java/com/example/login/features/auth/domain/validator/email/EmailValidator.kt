package com.example.login.features.auth.domain.validator.email

import com.example.login.features.auth.domain.validator.IValidator

class EmailValidator : IValidator<String> {
    override fun validate(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    override fun getErrorMessage(input: String): EmailError {
        return if (input.isEmpty()) {
            EmailError.EmptyEmail
        } else {
            EmailError.InvalidEmailFormat
        }
    }
}