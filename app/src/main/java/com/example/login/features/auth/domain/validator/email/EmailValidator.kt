package com.example.login.features.auth.domain.validator.email

import android.util.Patterns
import com.example.login.features.auth.domain.validator.IValidator
import java.util.regex.Pattern

class EmailValidator(private val emailPattern: Pattern = Patterns.EMAIL_ADDRESS) : IValidator<String> {
    override fun validate(input: String): Boolean {
        return emailPattern.matcher(input).matches()
    }

    override fun getErrorMessage(input: String): EmailError {
        return if (input.isEmpty()) {
            EmailError.EmptyEmail
        } else {
            EmailError.InvalidEmailFormat
        }
    }
}