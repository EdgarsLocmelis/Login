package com.example.login.core.user.data.mapper

import com.example.login.core.user.data.local.entity.UserEntity
import com.example.login.core.user.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapEntityToDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            email = entity.email,
            password = entity.password
        )
    }

    fun mapDomainToEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            email = user.email,
            password = user.password
        )
    }
}