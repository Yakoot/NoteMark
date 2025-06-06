package dev.mamkin.notemark.register.presentation.register

data class RegisterState(
    val usernameValue: String = "",
    val emailValue: String = "",
    val passwordValue: String = "",
    val repeatPasswordValue: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
    val buttonEnabled: Boolean = false,
)