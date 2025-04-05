package com.example.login.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.login.core.presentation.navigation.destinations.Destinations
import com.example.login.core.presentation.navigation.graphs.SubGraphs
import com.example.login.features.auth.presentation.screens.login.AuthScreen
import com.example.login.features.auth.presentation.screens.login.AuthViewModel
import com.example.login.features.auth.presentation.screens.splash.SplashScreen
import com.example.login.features.auth.presentation.screens.splash.SplashViewModel
import com.example.login.features.home.presentation.screens.HomeScreen
import com.example.login.features.home.presentation.screens.HomeViewModel

@Composable
fun Navigation() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = SubGraphs.Auth,
    ) {
        navigation<SubGraphs.Auth>(
            startDestination = Destinations.SplashScreen,
        ) {
            composable<Destinations.SplashScreen> {
                val viewModel = hiltViewModel<SplashViewModel>()
                val userUiState by viewModel.userUiState.collectAsStateWithLifecycle()

                SplashScreen(
                    userUiState = userUiState,
                    onNeedAuth = {
                        navHostController.navigate(Destinations.AuthScreen)
                    },
                    onAuthenticated = {
                        navHostController.navigate(Destinations.HomeScreen())
                    },
                    checkAuthState = viewModel::checkAuthState
                )
            }

            composable<Destinations.AuthScreen> {
                val viewModel = hiltViewModel<AuthViewModel>()
                val authFormState by viewModel.authFormState.collectAsStateWithLifecycle()

                AuthScreen(
                    authFormState = authFormState,
                    onEmailChanged = {
                        viewModel.resetAuthState()
                    },
                    onPasswordChanged = {
                        viewModel.resetAuthState()
                    },
                    login = { email, password ->
                        viewModel.login(email, password)
                    },
                    signUp = viewModel::signUp,
                    onSuccess = {
                        navHostController.navigate(Destinations.HomeScreen())
                    }
                )
            }
        }

        navigation<SubGraphs.Home>(
            startDestination = Destinations.HomeScreen::class,
        ) {
            composable<Destinations.HomeScreen> {
                val viewModel = hiltViewModel<HomeViewModel>()
                val userState by viewModel.userState.collectAsStateWithLifecycle()

                HomeScreen(
                    userState = userState
                ) {
                    viewModel.logout().invokeOnCompletion {
                        navHostController.navigate(Destinations.SplashScreen) {
                            popUpTo(SubGraphs.Home) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}