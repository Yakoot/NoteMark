package dev.mamkin.notemark.register.presentation.register

sealed interface RegisterAction {
    data class UsernameChanged(val value: String) : RegisterAction
    data class EmailChanged(val value: String) : RegisterAction
    data class PasswordChanged(val value: String) : RegisterAction
    data class RepeatPasswordChanged(val value: String) : RegisterAction
    object CreateAccountClicked : RegisterAction
    object AlreadyHaveAccountClicked : RegisterAction
}