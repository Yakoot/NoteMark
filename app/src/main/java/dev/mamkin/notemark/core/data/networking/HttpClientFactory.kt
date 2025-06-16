package dev.mamkin.notemark.core.data.networking

import dev.mamkin.notemark.BuildConfig
import dev.mamkin.notemark.auth.data.networking.dto.RefreshTokenRequest
import dev.mamkin.notemark.auth.data.networking.dto.RefreshTokenResponse
import dev.mamkin.notemark.core.data.datastore.TokenDataStore
import dev.mamkin.notemark.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(
        engine: HttpClientEngine,
        tokenStorage: TokenDataStore
    ): HttpClient {
        return HttpClient(engine) {
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenStorage.accessTokenFlow.first()
                        val refreshToken = tokenStorage.refreshTokenFlow.first()

                        BearerTokens(
                            accessToken = accessToken ?: "",
                            refreshToken = refreshToken ?: ""
                        )
                    }
                    refreshTokens {
                        val refreshToken = tokenStorage.refreshTokenFlow.first()
                        val response = safeCall<RefreshTokenResponse> {
                            client.post(
                                urlString = constructUrl("/api/auth/refresh")
                            ) {
                                contentType(ContentType.Application.Json)
                                setBody(
                                    RefreshTokenRequest(
                                        refreshToken = refreshToken ?: ""
                                    )
                                )
                                markAsRefreshTokenRequest()
//                            header("Debug", true)
//                            header("Debug", true)
                            }
                        }
                        if (response is Result.Success<RefreshTokenResponse>) {
                            BearerTokens(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken
                            )
                        } else {
                            BearerTokens(
                                refreshToken = "",
                                accessToken = ""
                            )
                        }
                    }
                }
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("X-User-Email", BuildConfig.API_KEY)
            }
        }
    }
}