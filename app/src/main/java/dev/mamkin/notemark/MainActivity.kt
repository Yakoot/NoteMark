package dev.mamkin.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
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
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Landing -> NavEntry(key) {
                    LandingRoot(
                        navigateToRegister = {
                            backStack.add(Register)
                        },
                        navigateToLogin = {
                            backStack.add(Login)
                        }
                    )
                }

                is Login -> NavEntry(key) {
                    LoginRoot()
                }

                is Register -> NavEntry(key) {
                    RegisterRoot()
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
