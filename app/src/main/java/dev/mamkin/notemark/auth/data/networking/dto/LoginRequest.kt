package dev.mamkin.notemark.auth.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val email: String
)