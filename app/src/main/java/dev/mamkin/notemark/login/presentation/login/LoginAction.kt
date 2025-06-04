package dev.mamkin.notemark.login.presentation.login

sealed interface LoginAction {
    data object OnRegisterClick : LoginAction
}