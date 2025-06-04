package dev.mamkin.notemark.landing.presentation.landing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.notemark.R
import dev.mamkin.notemark.core.presentation.designsystem.buttons.FilledButton
import dev.mamkin.notemark.core.presentation.designsystem.buttons.TextButton
import dev.mamkin.notemark.core.presentation.designsystem.theme.LandingBackground
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun LandingRoot(
    navigateToRegister: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
) {
    LandingScreen(
        onAction = {
            when (it) {
                LandingAction.OnGetStartedClicked -> navigateToRegister()
                LandingAction.OnLoginClicked -> navigateToLogin()
            }
        }
    )
}

@Composable
fun LandingScreen(
    onAction: (LandingAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) {
        val configuration = LocalConfiguration.current
        val orientation = configuration.orientation
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LandingBackground)
                .padding(it)
        ) {
            if (isPortrait) {
                Image(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(),
                    painter = painterResource(R.drawable.landing_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Surface(
                    modifier = Modifier
                        .height(322.dp)
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    CardContent(
                        modifier = Modifier.padding(
                            top = 30.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        onAction = onAction
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 19.dp, end = 15.dp)
                            .weight(1f),
                        painter = painterResource(R.drawable.landing_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Surface(
                        modifier = Modifier
                            .height(330.dp)
                            .width(480.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    ) {
                        CardContent(
                            modifier = Modifier
                                .padding(
                                    start = 60.dp,
                                    end = 40.dp,
                                    top = 40.dp,
                                    bottom = 40.dp
                                ),
                            onAction = onAction
                        )
                    }
                }


            }

        }
    }
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    onAction: (LandingAction) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Your Own Collection of\u00A0Notes",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Capture your thoughts and ideas.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(40.dp))
        FilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Get Started",
            onClick = {
                onAction(LandingAction.OnGetStartedClicked)
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Log In",
            onClick = {
                onAction(LandingAction.OnLoginClicked)
            }
        )
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 900, heightDp = 412)
@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        LandingScreen(
            onAction = {}
        )
    }
}