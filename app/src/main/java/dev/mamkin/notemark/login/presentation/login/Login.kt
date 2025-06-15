package dev.mamkin.notemark.login.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppFilledButton
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppTextButton
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.AppTextField
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.PasswordTextField
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.core.presentation.util.DeviceType
import dev.mamkin.notemark.core.presentation.util.ObserveAsEvents
import dev.mamkin.notemark.core.presentation.util.toString
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToRegister: () -> Unit = {},
    navigateToAuthorizedZone: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) {
        when (it) {
            LoginEvent.LoginSuccess -> navigateToAuthorizedZone()
            is LoginEvent.LoginError -> {
                Toast.makeText(
                    context,
                    it.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

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
    val hasTopPadding = deviceType == DeviceType.MOBILE_PORTRAIT
    val isTwoColumns = !deviceType.isPortrait()

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
                .padding(top = if (hasTopPadding) 8.dp else 0.dp),
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
                val isCentered = deviceType.isTablet()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 32.dp,
                            horizontal = 16.dp,
                        ),
                    horizontalAlignment = if (isCentered) Alignment.CenterHorizontally else Alignment.Start
                ) {
                    LoginTitle(
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    LoginForm(
                        modifier = Modifier
                            .widthIn(max = 560.dp),
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
    val focusManager = LocalFocusManager.current
    val (emailFocus, passwordFocus) = remember { FocusRequester.createRefs() }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .imePadding()
    ) {
        AppTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocus),
            value = state.emailValue,
            supportingText = state.emailError,
            onValueChange = {
                onAction(LoginAction.EmailChanged(it))
            },
            label = "Email",
            placeholder = "john.doe@example.com",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocus),
            value = state.passwordValue,
            supportingText = state.passwordError,
            onValueChange = {
                onAction(LoginAction.PasswordChanged(it))
            },
            label = "Password",
            placeholder = "Password",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onAction(LoginAction.LoginClicked)
                }
            )
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
            onAction = {},
        )
    }
}
