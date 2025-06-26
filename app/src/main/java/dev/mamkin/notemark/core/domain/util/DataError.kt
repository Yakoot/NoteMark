package dev.mamkin.notemark.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
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

    enum class Local: DataError {
        DISK_FULL
    }
}
