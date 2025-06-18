package dev.mamkin.notemark.auth.data.networking

import dev.mamkin.notemark.auth.data.networking.dto.CreateUserRequest
import dev.mamkin.notemark.auth.data.networking.dto.LoginRequest
import dev.mamkin.notemark.auth.data.networking.dto.LoginResponse
import dev.mamkin.notemark.auth.domain.AuthDataSource
import dev.mamkin.notemark.core.data.datastore.TokenDataStore
import dev.mamkin.notemark.core.data.datastore.UserProfileDataStore
import dev.mamkin.notemark.core.data.networking.constructUrl
import dev.mamkin.notemark.core.data.networking.safeCall
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.NetworkError
import dev.mamkin.notemark.core.domain.util.asEmptyDataResult
import dev.mamkin.notemark.core.domain.util.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RemoteAuthDataSource(
    private val httpClient: HttpClient,
    private val tokenDataStore: TokenDataStore,
    private val userProfileDataStore: UserProfileDataStore,
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
    ): EmptyResult<NetworkError> {
        val result = safeCall<LoginResponse> {
            httpClient.post(
                urlString = constructUrl("/api/auth/login")
            ) {
                setBody(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )
            }
        }

        result.onSuccess {
            tokenDataStore.saveTokens(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken
            )
            userProfileDataStore.updateUsername(it.username)
        }
        return result.asEmptyDataResult()
    }
}
