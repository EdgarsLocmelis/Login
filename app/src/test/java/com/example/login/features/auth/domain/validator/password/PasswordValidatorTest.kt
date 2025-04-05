package com.example.login.features.auth.domain.validator.password

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordValidatorTest {
    private lateinit var passwordValidator: PasswordValidator

    @Before
    fun setup() {
        passwordValidator = PasswordValidator()
    }

    @Test
    fun `validate returns true for valid passwords`() {
        val validPasswords = listOf(
            "Password123",
            "ComplexP@ssw0rd",
            "Abc123Def",
            "P@ssw0rd",
            "Str0ngP@ssw0rd",
            "P@ssw0rd123"
        )

        validPasswords.forEach { password ->
            assertTrue(passwordValidator.validate(password), "Password '$password' should be valid")
        }
    }

    @Test
    fun `validate returns false for invalid passwords`() {
        val invalidPasswords = listOf(
            "", // Empty
            "short", // Too short
            "nouppercase123", // No uppercase
            "NOLOWERCASE123", // No lowercase
            "NoNumbers", // No numbers
            "12345678", // No uppercase
            "abcdefgh", // No uppercase, no numbers
            "ABCDEFGH", // No lowercase, no numbers
            "Abc123" // Too short
        )

        invalidPasswords.forEach { password ->
            assertFalse(passwordValidator.validate(password), "Password '$password' should be invalid")
        }
    }

    @Test
    fun `getErrorMessage returns EmptyPassword for empty input`() {
        assertEquals(PasswordError.EmptyPassword, passwordValidator.getErrorMessage(""))
    }

    @Test
    fun `getErrorMessage returns PasswordTooShort for short passwords`() {
        val shortPasswords = listOf(
            "short",
            "Abc123",
            "Pass1",
            "P@ss1"
        )

        shortPasswords.forEach { password ->
            assertEquals(PasswordError.PasswordTooShort, passwordValidator.getErrorMessage(password))
        }
    }

    @Test
    fun `getErrorMessage returns PasswordTooWeak for passwords without required characters`() {
        val weakPasswords = listOf(
            "nouppercase123",
            "NOLOWERCASE123",
            "NoNumbers",
            "nouppercase",
            "NOLOWERCASE",
            "12345678"
        )

        weakPasswords.forEach { password ->
            assertEquals(PasswordError.PasswordTooWeak, passwordValidator.getErrorMessage(password))
        }
    }

    @Test
    fun `getErrorMessage returns WrongPassword for valid format but incorrect password`() {
        val validFormatPasswords = listOf(
            "Password123",
            "ComplexP@ssw0rd",
            "Abc123Def",
            "P@ssw0rd",
            "Str0ngP@ssw0rd",
            "P@ssw0rd123"
        )

        validFormatPasswords.forEach { password ->
            assertEquals(PasswordError.WrongPassword, passwordValidator.getErrorMessage(password))
        }
    }
} 