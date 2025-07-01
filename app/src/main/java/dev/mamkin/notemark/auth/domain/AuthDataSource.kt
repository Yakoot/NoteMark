package dev.mamkin.notemark.auth.domain

import dev.mamkin.notemark.core.domain.util.DataError
import dev.mamkin.notemark.core.domain.util.EmptyResult

interface AuthDataSource {
    suspend fun createUser(
        username: String,
        password: String,
        email: String
    ): EmptyResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
}
