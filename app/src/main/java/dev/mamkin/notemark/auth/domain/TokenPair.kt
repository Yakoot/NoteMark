package dev.mamkin.notemark.auth.domain

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
