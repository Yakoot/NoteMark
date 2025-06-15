package dev.mamkin.notemark.register.presentation.register

import dev.mamkin.notemark.core.domain.util.NetworkError

interface RegisterEvent {
    data object RegisterSuccess : RegisterEvent
    data class RegisterError(
        val error: NetworkError
    ) : RegisterEvent
}