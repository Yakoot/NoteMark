package dev.mamkin.notemark.login.presentation.login

sealed interface LoginAction {
    data class EmailChanged(val value: String): LoginAction
    data class PasswordChanged(val value: String): LoginAction
    object LoginClicked: LoginAction
    object DontHaveAccountClicked: LoginAction
}