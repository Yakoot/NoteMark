package dev.mamkin.notemark.notes.presentation.editNote

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.notemark.core.presentation.designsystem.buttons.AppTextButton
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.TransparentHintTextField
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.core.presentation.designsystem.theme.topBarAction
import dev.mamkin.notemark.core.presentation.util.DeviceType
import dev.mamkin.notemark.core.presentation.util.ObserveAsEvents
import dev.mamkin.notemark.notes.presentation.editNote.components.DiscardChangesDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditNoteRoot(
    id: String,
    isNew: Boolean = true,
    viewModel: EditNoteViewModel = koinViewModel(
        parameters = { parametersOf(id, isNew) },
        key = id
    ),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var openAlertDialog by remember { mutableStateOf(false) }


    BackHandler {
        viewModel.onAction(EditNoteAction.Close)
    }


    ObserveAsEvents(viewModel.events) {
        when (it) {
            is EditNoteEvent.Close -> navigateBack()
            is EditNoteEvent.ShowDiscardDialog -> {
                openAlertDialog = true
            }
        }
    }

    if (openAlertDialog) {
        DiscardChangesDialog(
            onDismissRequest = {
                openAlertDialog = false
            },
            onConfirm = {
                openAlertDialog = false
                navigateBack()
                viewModel.clear()
            }
        )
    }

    EditNoteScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    state: EditNoteState,
    onAction: (EditNoteAction) -> Unit,
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
                            onAction(EditNoteAction.Close)
                        },
                        onSaveClick = {
                            onAction(EditNoteAction.SaveNote)
                        }
                    )
                }
            ) {
                NoteForm(
                    modifier = Modifier.padding(it),
                    title = state.title,
                    onTitleChange = { onAction(EditNoteAction.OnTitleChange(it)) },
                    content = state.content,
                    onContentChange = { onAction(EditNoteAction.OnContentChange(it)) }
                )
            }
        }

        DeviceType.MOBILE_LANDSCAPE -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                TopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -12.dp)
                    ,
                    closeButtonPadding = 44.dp,
                    onCloseClick = {
                        onAction(EditNoteAction.Close)
                    },
                    onSaveClick = {
                        onAction(EditNoteAction.SaveNote)
                    }
                )
                NoteForm(
                    modifier = Modifier
                        .statusBarsPadding()
                        .widthIn(max = 540.dp)
                    ,
                    title = state.title,
                    onTitleChange = { onAction(EditNoteAction.OnTitleChange(it)) },
                    content = state.content,
                    onContentChange = { onAction(EditNoteAction.OnContentChange(it)) }
                )
            }
        }

        DeviceType.TABLET_PORTRAIT,
        DeviceType.TABLET_LANDSCAPE,
        DeviceType.DESKTOP,
            -> {
            Scaffold(
                topBar = {
                    TopBar(
                        modifier = Modifier,
                        saveButtonPadding = 24.dp,
                        closeButtonPadding = 8.dp,
                        onCloseClick = {
                            onAction(EditNoteAction.Close)
                        },
                        onSaveClick = {
                            onAction(EditNoteAction.SaveNote)
                        }
                    )
                }
            ) {
                NoteForm(
                    modifier = Modifier.padding(it),
                    title = state.title,
                    onTitleChange = { onAction(EditNoteAction.OnTitleChange(it)) },
                    content = state.content,
                    onContentChange = { onAction(EditNoteAction.OnContentChange(it)) }
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
    saveButtonPadding: Dp = 16.dp,
    closeButtonPadding: Dp = 0.dp,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Icon(
                modifier = Modifier
                    .padding(start = closeButtonPadding)
                    .clickable(onClick = onCloseClick),
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
            AppTextButton(
                text = "SAVE NOTE",
                textStyle = MaterialTheme.typography.topBarAction,
                onClick = onSaveClick
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        EditNoteScreen(
            state = EditNoteState(),
            onAction = {}
        )
    }
}
