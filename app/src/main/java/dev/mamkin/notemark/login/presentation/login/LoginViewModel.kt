package dev.mamkin.notemark.login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.notemark.core.domain.util.onError
import dev.mamkin.notemark.core.domain.util.onSuccess
import dev.mamkin.notemark.main.domain.AuthDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authDataSource: AuthDataSource
) : ViewModel() {

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged -> onEmailChanged(action.value)
            LoginAction.LoginClicked -> onLoginClicked()
            is LoginAction.PasswordChanged -> onPasswordChanged(action.value)
            else -> {}
        }
    }

    private fun onEmailChanged(value: String) {
        _state.update {
            it.copy(
                emailValue = value,
                buttonEnabled = value.isNotBlank() && state.value.passwordValue.isNotBlank()
            )
        }
    }

    private fun onPasswordChanged(value: String) {
        _state.update {
            it.copy(
                passwordValue = value,
                buttonEnabled = value.isNotBlank() && state.value.emailValue.isNotBlank()
            )
        }
    }

    private fun onLoginClicked() {
        _state.update { it.copy(buttonLoading = true) }
        viewModelScope.launch {
            val response = authDataSource.login(
                password = state.value.passwordValue,
                email = state.value.emailValue
            )
            _state.update { it.copy(buttonLoading = false) }
            response
                .onSuccess {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
                .onError {
                    eventChannel.send(LoginEvent.LoginError(it))
                }
        }
    }
}