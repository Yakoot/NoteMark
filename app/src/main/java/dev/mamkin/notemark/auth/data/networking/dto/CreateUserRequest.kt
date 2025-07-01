package dev.mamkin.notemark.auth.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val username: String,
    val password: String,
    val email: String
)