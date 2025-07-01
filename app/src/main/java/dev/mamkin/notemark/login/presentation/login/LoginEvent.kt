package dev.mamkin.notemark.login.presentation.login

import dev.mamkin.notemark.core.domain.util.DataError

interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class LoginError(val error: DataError.Network) : LoginEvent
}
