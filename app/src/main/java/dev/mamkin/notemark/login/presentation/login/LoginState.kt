package dev.mamkin.notemark.login.presentation.login

data class LoginState(
    val emailValue: String = "",
    val passwordValue: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val buttonEnabled: Boolean = false,
)