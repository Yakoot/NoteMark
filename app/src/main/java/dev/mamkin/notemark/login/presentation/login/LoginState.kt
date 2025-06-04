package dev.mamkin.notemark.login.presentation.login

data class LoginState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)