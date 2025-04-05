package com.example.login.core.presentation.utils

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Utility functions for handling I/O operations with minimum processing time
 * to improve user experience.
 */
object IoProcessingUtils {

    suspend fun <T> withMinimumProcessingTime(
        minimumTime: Duration = 500.milliseconds,
        operation: suspend () -> T
    ): T {
        val startTime = System.currentTimeMillis()

        val result = operation()

        val processingTime = System.currentTimeMillis() - startTime
        val minimumTimeMs = minimumTime.inWholeMilliseconds

        if (processingTime < minimumTimeMs) {
            delay(minimumTimeMs - processingTime)
        }

        return result
    }
}