package dev.mamkin.notemark.auth.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val refreshToken: String,
    val accessToken: String,
    val username: String
)