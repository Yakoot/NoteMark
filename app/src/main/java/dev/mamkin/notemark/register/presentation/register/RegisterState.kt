package dev.mamkin.notemark.register.presentation.register

data class RegisterState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)