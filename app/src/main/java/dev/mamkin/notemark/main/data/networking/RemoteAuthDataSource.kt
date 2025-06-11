package dev.mamkin.notemark.main.data.networking

import dev.mamkin.notemark.core.data.datastore.TokenDataStore
import dev.mamkin.notemark.core.data.networking.constructUrl
import dev.mamkin.notemark.core.data.networking.safeCall
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.NetworkError
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.core.domain.util.map
import dev.mamkin.notemark.main.data.networking.dto.CreateUserRequest
import dev.mamkin.notemark.main.data.networking.dto.LoginRequest
import dev.mamkin.notemark.main.data.networking.dto.LoginResponse
import dev.mamkin.notemark.main.domain.AuthDataSource
import dev.mamkin.notemark.main.domain.TokenPair
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RemoteAuthDataSource(
    private val httpClient: HttpClient,
) : AuthDataSource {

    override suspend fun createUser(
        username: String,
        password: String,
        email: String
    ): EmptyResult<NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("/api/auth/register")
            ) {
                setBody(
                    CreateUserRequest(
                        username = username,
                        password = password,
                        email = email
                    )
                )
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<TokenPair, NetworkError> {
        return safeCall<LoginResponse> {
            httpClient.post(
                urlString = constructUrl("/api/auth/login")
            ) {
                LoginRequest(
                    email = email,
                    password = password
                )
            }
        }.map { it -> TokenPair(it.accessToken, it.refreshToken) }
    }
}