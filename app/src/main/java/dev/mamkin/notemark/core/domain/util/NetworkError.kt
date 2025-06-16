package dev.mamkin.notemark.core.domain.util

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