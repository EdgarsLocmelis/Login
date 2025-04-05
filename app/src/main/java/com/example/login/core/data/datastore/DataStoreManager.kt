package com.example.login.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Datastore manager class for storing and retrieving data from DataStore.
 */
@Singleton
class DatastoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun <T> storeKeyData(key: Preferences.Key<T>, data: T) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[key] = data
        }
    }

    fun <T> getKeyData(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    suspend fun <T> deleteKey(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}