package dev.mamkin.notemark.register.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
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
            initialValue = RegisterState()
        )

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.AlreadyHaveAccountClicked -> {}
            RegisterAction.CreateAccountClicked -> {}
            is RegisterAction.EmailChanged -> onEmailChanged(action.value)
            is RegisterAction.PasswordChanged -> onPasswordChanged(action.value)
            is RegisterAction.RepeatPasswordChanged -> onRepeatPasswordChanged(action.value)
            is RegisterAction.UsernameChanged -> onUsernameChanged(action.value)
        }
    }

    private fun onUsernameChanged(value: String) {
        _state.update {
            it.copy(usernameValue = value)
        }
    }

    private fun onEmailChanged(value: String) {
        _state.update {
            it.copy(emailValue = value)
        }
    }

    private fun onPasswordChanged(value: String) {
        _state.update {
            it.copy(passwordValue = value)
        }
    }

    private fun onRepeatPasswordChanged(value: String) {
        _state.update {
            it.copy(repeatPasswordValue = value)
        }
    }




}