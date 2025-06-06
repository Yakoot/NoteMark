package dev.mamkin.notemark.login.presentation.login

import android.R.attr.end
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppFilledButton
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppTextButton
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.AppTextField
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.core.presentation.utils.DeviceType
import dev.mamkin.notemark.register.presentation.register.RegisterAction

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = viewModel(),
    navigateToRegister: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = {
            when (it) {
                is LoginAction.DontHaveAccountClicked -> {
                    navigateToRegister()
                }
                else -> {
                    viewModel.onAction(it)
                }
            }
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowSizeClass(windowSizeClass)

    val isTwoColumns = deviceType == DeviceType.TABLET_LANDSCAPE || deviceType == DeviceType.MOBILE_LANDSCAPE
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
            if(isTwoColumns) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 60.dp,
                            end = 40.dp,
                            top = 32.dp,
                        )
                    ,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    LoginTitle(
                        modifier = Modifier.weight(1f)
                    )
                    LoginForm(
                        modifier = Modifier.weight(1f),
                        state = state,
                        onAction = onAction
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 32.dp,
                            horizontal = 16.dp,
                        )
                ) {
                    LoginTitle(
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    LoginForm(
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onAction = onAction
                    )
                }
            }

        }
    }
}

@Composable
fun LoginTitle(modifier: Modifier = Modifier) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowSizeClass(windowSizeClass)
    val isCentered = deviceType.isTablet()
    Column(
        modifier = modifier,
        horizontalAlignment = if (isCentered) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Capture your thoughts and ideas.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.emailValue,
            supportingText = state.emailError,
            onValueChange = {
                onAction(LoginAction.EmailChanged(it))
            },
            label = "Email",
            placeholder = "john.doe@example.com"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.passwordValue,
            supportingText = state.passwordError,
            onValueChange = {
                onAction(LoginAction.PasswordChanged(it))
            },
            label = "Password",
            placeholder = "Password"
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Log in",
            onClick = {
                onAction(LoginAction.LoginClicked)
            },
            enabled = state.buttonEnabled
        )
        Spacer(modifier = Modifier.height(12.dp))
        AppTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Don’t have an account?",
            onClick = {
                onAction(LoginAction.DontHaveAccountClicked)
            }
        )
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    NoteMarkTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}