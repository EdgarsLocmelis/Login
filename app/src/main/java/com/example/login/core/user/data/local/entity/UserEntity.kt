package com.example.login.core.user.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    val email: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)