package dev.mamkin.notemark.core.data.networking

import android.R.attr.level
import android.net.http.HttpResponseCache.install
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
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
//                            accessToken = tokenPair?.accessToken ? "",
//                        refreshToken = tokenPair?.refreshToken ? ""
                            "", ""
                        )
                    }
                    refreshTokens {
                        BearerTokens(
                            accessToken = "",
                            refreshToken = ""
                        )
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
            }
        }
    }
}