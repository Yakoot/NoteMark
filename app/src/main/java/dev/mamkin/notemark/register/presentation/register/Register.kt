package dev.mamkin.notemark.register.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppFilledButton
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppTextButton
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.AppTextField
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.PasswordTextField
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = viewModel(),
    navigateToLogin: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onAction = {
            when (it) {
                is RegisterAction.AlreadyHaveAccountClicked -> {
                    navigateToLogin()
                }
                else -> {
                    viewModel.onAction(it)
                }
            }
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 8.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = 32.dp,
                    horizontal = 16.dp,
                )
            ) {
                Text(
                    text = "Create account",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Capture your thoughts and ideas.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(40.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.usernameValue,
                    onValueChange = {
                        onAction(RegisterAction.UsernameChanged(it))
                    },
                    supportingText = state.usernameError,
                    label = "Username",
                    placeholder = "John.doe"
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.emailValue,
                    supportingText = state.emailError,
                    onValueChange = {
                        onAction(RegisterAction.EmailChanged(it))
                    },
                    label = "Email",
                    placeholder = "john.doe@example.com"
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.passwordValue,
                    supportingText = state.passwordError,
                    onValueChange = {
                        onAction(RegisterAction.PasswordChanged(it))
                    },
                    label = "Password",
                    placeholder = "Password"
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.repeatPasswordValue,
                    supportingText = state.repeatPasswordError,
                    onValueChange = {
                        onAction(RegisterAction.RepeatPasswordChanged(it))
                    },
                    label = "Repeat password",
                    placeholder = "Password"
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create account",
                    onClick = {
                        onAction(RegisterAction.CreateAccountClicked)
                    },
                    enabled = state.buttonEnabled
                )
                Spacer(modifier = Modifier.height(12.dp))
                AppTextButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Already have an account?",
                    onClick = {
                        onAction(RegisterAction.AlreadyHaveAccountClicked)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}