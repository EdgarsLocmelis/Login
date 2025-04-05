package com.example.login.features.auth.domain.validator.email

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import java.util.regex.Pattern

class EmailValidatorTest {
    private lateinit var emailValidator: EmailValidator
    private lateinit var emailPattern: Pattern

    @Before
    fun setup() {
        // This is the regex pattern used by Android for email validation
        emailPattern = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
        )
        emailValidator = EmailValidator(emailPattern)
    }

    @Test
    fun `validate returns true for valid email addresses`() {
        val validEmails = listOf(
            "test@example.com",
            "user.name@domain.com",
            "user+label@domain.co.uk",
            "user@subdomain.domain.com",
            "user@domain-with-dash.com",
            "user@domain.com",
            "user@domain.verylongtld"
        )

        validEmails.forEach { email ->
            assertTrue(emailValidator.validate(email), "Email '$email' should be valid")
        }
    }

    @Test
    fun `validate returns false for invalid email addresses`() {
        val invalidEmails = listOf(
            "",
            "invalid-email",
            "user@",
            "@domain.com",
            "user@domain.",
            "user@.com",
            "user@domain..com",
            "user@domain.com.",
            "user@domain.com..",
            "user@domain.com@domain.com",
            "user@domain.com@",
            "user@domain.com@domain",
            "user@domain.com@domain.com@domain.com"
        )

        invalidEmails.forEach { email ->
            assertFalse(emailValidator.validate(email), "Email '$email' should be invalid")
        }
    }

    @Test
    fun `getErrorMessage returns EmptyEmail for empty input`() {
        assertEquals(EmailError.EmptyEmail, emailValidator.getErrorMessage(""))
    }

    @Test
    fun `getErrorMessage returns InvalidEmailFormat for invalid email`() {
        val invalidEmails = listOf(
            "invalid-email",
            "user@",
            "@domain.com",
            "user@domain.",
            "user@.com"
        )

        invalidEmails.forEach { email ->
            assertEquals(EmailError.InvalidEmailFormat, emailValidator.getErrorMessage(email))
        }
    }
} 