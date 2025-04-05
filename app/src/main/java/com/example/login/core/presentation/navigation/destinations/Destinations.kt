package com.example.login.core.presentation.navigation.destinations

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object SplashScreen: Destinations()
    @Serializable
    data object AuthScreen: Destinations()
    @Serializable
    data class HomeScreen(val data: String = ""): Destinations()
}