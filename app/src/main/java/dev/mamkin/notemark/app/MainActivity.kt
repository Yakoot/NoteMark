package dev.mamkin.notemark.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.landing.presentation.landing.LandingRoot
import dev.mamkin.notemark.login.presentation.login.LoginRoot
import dev.mamkin.notemark.register.presentation.register.RegisterRoot
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            NoteMarkTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Landing)
    NavDisplay(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLowest),
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
                            backStack.add(BlankPageAfterLogIn)
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

                is BlankPageAfterLogIn -> NavEntry(key) {
                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentWindowInsets = WindowInsets.statusBars
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                text = "You are successfully logged in!",
                                color = Color.White
                            )
                        }
                    }
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
data object BlankPageAfterLogIn: NavKey
