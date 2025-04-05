package com.example.login.features.auth.data

import com.example.login.core.user.data.local.datastore.UserDataStoreManager
import com.example.login.features.auth.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userdataStoreManager: UserDataStoreManager,
): IAuthRepository {
    override suspend fun getAuthorisedUserId(): Long? {
        return userdataStoreManager.getUserId().firstOrNull()
    }

    override suspend fun saveAuthorisedUserId(userId: Long) {
        userdataStoreManager.setUserId(userId)
    }

    override suspend fun resetAuthorisedId() {
        userdataStoreManager.clearUserKey()
    }
}