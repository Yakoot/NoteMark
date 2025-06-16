package dev.mamkin.notemark.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.mamkin.notemark.core.presentation.util.ObserveAsEvents
import dev.mamkin.notemark.landing.presentation.landing.LandingRoot
import dev.mamkin.notemark.login.presentation.login.LoginRoot
import dev.mamkin.notemark.notes.presentation.notes.NotesRoot
import dev.mamkin.notemark.register.presentation.register.RegisterRoot
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = koinViewModel()
) {
    val backStack = rememberNavBackStack(Notes)
    ObserveAsEvents(
        events = viewModel.events,
        onEvent = {
            when (it) {
                MainScreenEvent.NavigateToLandingPage -> {
                    backStack.add(Landing)
                    backStack.remove(Notes)
                }
            }
        }
    )
    NavDisplay(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainerLowest),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Landing -> NavEntry(key) {
                    LandingRoot(
                        navigateToRegister = {
                            backStack.add(Register)
                            backStack.remove(Landing)
                        },
                        navigateToLogin = {
                            backStack.add(Login)
                            backStack.remove(Landing)
                        }
                    )
                }

                is Login -> NavEntry(key) {
                    LoginRoot(
                        navigateToRegister = {
                            backStack.add(Register)
                            backStack.remove(Register)
                        },
                        navigateToAuthorizedZone = {
                            backStack.add(Notes)
                            backStack.remove(Login)
                        }
                    )
                }

                is Register -> NavEntry(key) {
                    RegisterRoot(
                        navigateToLogin = {
                            backStack.remove(Login)
                            backStack.add(Login)
                        }
                    )
                }

                is Notes -> NavEntry(key) {
                    NotesRoot()
                }

                else -> throw IllegalArgumentException("Unknown key: $key")
            }
        }
    )

}

@Serializable
data object Landing: NavKey
@Serializable
data object Login: NavKey
@Serializable
data object Register: NavKey
@Serializable
data object Notes: NavKey