package dev.mamkin.notemark.notes.presentation.createNote

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.TransparentHintTextField
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.core.presentation.designsystem.theme.topBarAction
import dev.mamkin.notemark.core.presentation.util.DeviceType
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNoteRoot(
    viewModel: CreateNoteViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateNoteScreen(
        state = state,
        onAction = {
            when (it) {
                CreateNoteAction.Close -> navigateBack()
                else -> {
                    viewModel.onAction(it)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    state: CreateNoteState,
    onAction: (CreateNoteAction) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowSizeClass(windowSizeClass)

    when (deviceType) {
        DeviceType.MOBILE_PORTRAIT -> {
            Scaffold(
                topBar = {
                    TopBar(
                        modifier = Modifier,
                        onCloseClick = {
                            onAction(CreateNoteAction.Close)
                        },
                        onSaveClick = {
                            onAction(CreateNoteAction.SaveNote)
                        }
                    )
                }
            ) {
                NoteForm(
                    modifier = Modifier.padding(it),
                    title = state.title,
                    onTitleChange = { onAction(CreateNoteAction.OnTitleChange(it)) },
                    content = state.content,
                    onContentChange = { onAction(CreateNoteAction.OnContentChange(it)) }
                )
            }
        }

        DeviceType.MOBILE_LANDSCAPE -> {

        }

        DeviceType.TABLET_PORTRAIT,
        DeviceType.TABLET_LANDSCAPE,
        DeviceType.DESKTOP,
            -> {
            Scaffold(
                topBar = {
                    TopBar(
                        modifier = Modifier,
                        onCloseClick = {
                            onAction(CreateNoteAction.Close)
                        },
                        onSaveClick = {
                            onAction(CreateNoteAction.SaveNote)
                        }
                    )
                }
            ) {
                NoteForm(
                    modifier = Modifier.padding(it),
                    title = state.title,
                    onTitleChange = { onAction(CreateNoteAction.OnTitleChange(it)) },
                    content = state.content,
                    onContentChange = { onAction(CreateNoteAction.OnContentChange(it)) }
                )
            }
        }
    }
}

@Composable
private fun NoteForm(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .fillMaxSize()
    ) {
        TransparentHintTextField(
            text = title,
            onValueChange = onTitleChange,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            hintText = "Note title",
            modifier = Modifier
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surface
        )
        TransparentHintTextField(
            text = content,
            onValueChange = onContentChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            hintText = "Note content",
            modifier = Modifier,
            maxLines = Int.MAX_VALUE
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Icon(
                modifier = Modifier.clickable(onClick = onCloseClick),
                imageVector = Icons.Rounded.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            Text(
                text = "SAVE NOTE",
                style = MaterialTheme.typography.topBarAction,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onSaveClick)
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        CreateNoteScreen(
            state = CreateNoteState(),
            onAction = {}
        )
    }
}
