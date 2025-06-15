package dev.mamkin.notemark.main.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val email: String
)