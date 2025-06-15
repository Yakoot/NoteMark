package dev.mamkin.notemark.main.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val refreshToken: String,
    val accessToken: String
)