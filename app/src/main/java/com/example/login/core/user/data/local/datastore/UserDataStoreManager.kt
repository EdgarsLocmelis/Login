package com.example.login.core.user.data.local.datastore

import androidx.datastore.preferences.core.longPreferencesKey
import com.example.login.core.data.datastore.DatastoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDataStoreManager @Inject constructor(
    private val datastoreManager: DatastoreManager
) {
    companion object {
        val USER_ID_KEY = longPreferencesKey("user_id")
    }

    fun getUserId(): Flow<Long?> {
        return datastoreManager.getKeyData(USER_ID_KEY)
    }

    suspend fun setUserId(userId: Long) {
        datastoreManager.storeKeyData(USER_ID_KEY, userId)
    }

    suspend fun clearUserKey() {
        datastoreManager.deleteKey(USER_ID_KEY)
    }
}