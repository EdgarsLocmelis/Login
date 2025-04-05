package com.example.login.features.auth.domain.validator.password

import com.example.login.features.auth.domain.validator.IValidator

class PasswordValidator: IValidator<String> {
    override fun validate(input: String): Boolean {
        return input.length >= 8 &&
                input.any { it.isDigit() } &&
                input.any { it.isUpperCase() }
    }

    override fun getErrorMessage(input: String): PasswordError {
        return if (input.isEmpty()) {
            PasswordError.EmptyPassword
        } else if (input.length < 8) {
            PasswordError.PasswordTooShort
        } else if (!input.any { it.isDigit() } || !input.any { it.isUpperCase() }) {
            PasswordError.PasswordTooWeak
        } else {
            PasswordError.WrongPassword
        }
    }
}