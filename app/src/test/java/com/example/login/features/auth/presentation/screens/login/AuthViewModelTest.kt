package com.example.login.features.auth.presentation.screens.login

import android.content.Context
import com.example.login.core.user.domain.manager.UserManager
import com.example.login.core.user.domain.model.User
import com.example.login.features.auth.domain.AuthResult
import com.example.login.features.auth.domain.usecase.LoginUserUseCase
import com.example.login.features.auth.domain.usecase.SignUpUserUseCase
import com.example.login.features.auth.domain.validator.GeneralError
import com.example.login.features.auth.domain.validator.email.EmailError
import com.example.login.features.auth.domain.validator.password.PasswordError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private lateinit var viewModel: AuthViewModel
    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var signUpUserUseCase: SignUpUserUseCase
    private lateinit var userManager: UserManager
    private lateinit var context: Context
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginUserUseCase = mockk()
        signUpUserUseCase = mockk()
        userManager = mockk(relaxed = true)
        context = mockk(relaxed = true)
        viewModel = AuthViewModel(loginUserUseCase, signUpUserUseCase, userManager, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates state correctly`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val user = User(1, email, password)
        coEvery { loginUserUseCase(email, password) } returns flowOf(AuthResult.Success(user))

        viewModel.onEmailChanged(email)
        viewModel.onPasswordChanged(password)
        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.authFormState.value
        assertTrue(state.isAuthorized)
        assertTrue(state.isSubmitting)
        assertEquals(null, state.emailError)
        assertEquals(null, state.passwordError)
        coVerify { userManager.setUser(user) }
    }

    @Test
    fun `login with invalid email shows error`() = runTest {
        val email = "invalid-email"
        val password = "password123"
        coEvery { loginUserUseCase(email, password) } returns flowOf(
            AuthResult.Error(EmailError.InvalidEmailFormat)
        )
        coEvery { context.getString(EmailError.InvalidEmailFormat.messageResId) } returns "Invalid email"

        viewModel.onEmailChanged(email)
        viewModel.onPasswordChanged(password)
        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.authFormState.value
        assertFalse(state.isAuthorized)
        assertFalse(state.isSubmitting)
        assertEquals("Invalid email", state.emailError)
        assertEquals(null, state.passwordError)
    }

    @Test
    fun `login with invalid password shows error`() = runTest {
        val email = "test@example.com"
        val password = "123"
        coEvery { loginUserUseCase(email, password) } returns flowOf(
            AuthResult.Error(PasswordError.PasswordTooShort)
        )
        coEvery { context.getString(PasswordError.PasswordTooShort.messageResId) } returns "Password too short"

        viewModel.onEmailChanged(email)
        viewModel.onPasswordChanged(password)
        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.authFormState.value
        assertFalse(state.isAuthorized)
        assertFalse(state.isSubmitting)
        assertEquals(null, state.emailError)
        assertEquals("Password too short", state.passwordError)
    }

    @Test
    fun `signup success updates state correctly`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val user = User(1, email, password)
        coEvery { signUpUserUseCase(email, password) } returns flowOf(AuthResult.Success(user))

        viewModel.onEmailChanged(email)
        viewModel.onPasswordChanged(password)
        viewModel.signUp()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.authFormState.value
        assertTrue(state.isAuthorized)
        assertTrue(state.isSubmitting)
        assertEquals(null, state.emailError)
        assertEquals(null, state.passwordError)
        coVerify { userManager.setUser(user) }
    }

    @Test
    fun `signup with unexpected error shows general error`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        coEvery { signUpUserUseCase(email, password) } returns flowOf(
            AuthResult.Error(GeneralError.UnexpectedError)
        )
        coEvery { context.getString(GeneralError.UnexpectedError.messageResId) } returns "Unexpected error"

        viewModel.onEmailChanged(email)
        viewModel.onPasswordChanged(password)
        viewModel.signUp()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.authFormState.value
        assertFalse(state.isAuthorized)
        assertFalse(state.isSubmitting)
        assertEquals("", state.emailError)
        assertEquals("Unexpected error", state.passwordError)
    }

    @Test
    fun `resetFieldErrors clears all errors and flags`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        coEvery { loginUserUseCase(email, password) } returns flowOf(
            AuthResult.Error(EmailError.InvalidEmailFormat)
        )
        coEvery { context.getString(EmailError.InvalidEmailFormat.messageResId) } returns "Invalid email"

        viewModel.onEmailChanged(email)
        viewModel.onPasswordChanged(password)
        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.resetFieldErrors()

        val state = viewModel.authFormState.value
        assertFalse(state.isAuthorized)
        assertFalse(state.isSubmitting)
        assertEquals(null, state.emailError)
        assertEquals(null, state.passwordError)
    }
} 