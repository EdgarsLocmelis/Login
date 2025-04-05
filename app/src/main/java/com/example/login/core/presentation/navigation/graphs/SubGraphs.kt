package com.example.login.core.presentation.navigation.graphs

import kotlinx.serialization.Serializable

sealed class SubGraphs {
    @Serializable
    data object Auth: SubGraphs()

    @Serializable
    data object Home: SubGraphs()
}