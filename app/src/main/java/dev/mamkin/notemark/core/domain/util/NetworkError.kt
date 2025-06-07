package dev.mamkin.notemark.core.domain.util

import dev.mamkin.notemark.core.domain.util.Error

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    BAD_REQUEST,
    UNAUTHORIZED,
    METHOD_NOT_ALLOWED,
    CONFLICT,
    UNKNOWN,
}