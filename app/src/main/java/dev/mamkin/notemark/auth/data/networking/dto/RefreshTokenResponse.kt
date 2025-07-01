package dev.mamkin.notemark.auth.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val refreshToken: String,
    val accessToken: String
)