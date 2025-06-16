package dev.mamkin.notemark.auth.domain

import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.NetworkError
import dev.mamkin.notemark.core.domain.util.Result

interface AuthDataSource {
    suspend fun createUser(
        username: String,
        password: String,
        email: String
    ): EmptyResult<NetworkError>
    suspend fun login(email: String, password: String): Result<TokenPair, NetworkError>
}