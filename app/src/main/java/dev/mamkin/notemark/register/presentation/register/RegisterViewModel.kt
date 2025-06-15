package dev.mamkin.notemark.register.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.notemark.core.domain.util.onError
import dev.mamkin.notemark.core.domain.util.onSuccess
import dev.mamkin.notemark.main.domain.AuthDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authDataSource: AuthDataSource
) : ViewModel() {
    private val emailRegex = """^[A-Za-z0-9+_\.-]+@([A-Za-z0-9-]+\.)+[A-Za-z]{2,}$""".toRegex()

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _usernameValue = MutableStateFlow("")
    private val _emailValue = MutableStateFlow("")
    private val _passwordValue = MutableStateFlow("")
    private val _repeatPasswordValue = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)

    val state = combine(
        _usernameValue,
        _emailValue,
        _passwordValue,
        _repeatPasswordValue,
        _isLoading
    ) { username, email, password, repeatPassword, isLoading ->
        val usernameError =
            if (username.isEmpty() || isValidUsername(username)) null else "Username must be at least 3 characters."
        val emailError =
            if (email.isEmpty() || isValidEmail(email)) null else "Invalid email provided"
        val passwordError =
            if (password.isEmpty() || isValidPassword(password)) null else "Password must be at least 8 characters and include a number or symbol."
        val repeatPasswordError =
            if (repeatPassword.isEmpty() || password == repeatPassword) null else "Passwords do not match"

        val isFormValid =
            usernameError == null && emailError == null && passwordError == null && repeatPasswordError == null
                    && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()


        RegisterState(
            usernameValue = username,
            usernameError = usernameError,
            emailValue = email,
            emailError = emailError,
            passwordValue = password,
            passwordError = passwordError,
            repeatPasswordValue = repeatPassword,
            repeatPasswordError = repeatPasswordError,
            buttonEnabled = isFormValid,
            buttonLoading = isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = RegisterState()
    )

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.AlreadyHaveAccountClicked -> { /* Handle navigation */
            }

            RegisterAction.CreateAccountClicked -> onCreateAccountClicked()
            is RegisterAction.EmailChanged -> _emailValue.value = action.value
            is RegisterAction.PasswordChanged -> _passwordValue.value = action.value
            is RegisterAction.RepeatPasswordChanged -> _repeatPasswordValue.value = action.value
            is RegisterAction.UsernameChanged -> _usernameValue.value = action.value
        }
    }

    private fun onCreateAccountClicked() {
        if (state.value.buttonEnabled) {
            viewModelScope.launch {
                _isLoading.value = true
                val result = authDataSource.createUser(
                    username = state.value.usernameValue,
                    password = state.value.passwordValue,
                    email = state.value.emailValue
                )
                _isLoading.value = false
                result
                    .onSuccess {
                        eventChannel.send(RegisterEvent.RegisterSuccess)
                    }
                    .onError {
                        eventChannel.send(RegisterEvent.RegisterError(it))
                    }
            }
        }
    }

    private fun isValidUsername(username: String): Boolean {
        return username.length in 3..20
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex)
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }
        val hasNumberOrSymbol = password.any { it.isDigit() || !it.isLetterOrDigit() }
        return hasNumberOrSymbol
    }
}
