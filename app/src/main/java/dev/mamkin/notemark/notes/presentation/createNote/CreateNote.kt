package dev.mamkin.notemark.notes.presentation.createNote

import android.R.attr.maxLines
import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.notemark.core.presentation.designsystem.text_fields.TransparentHintTextField
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.core.presentation.designsystem.theme.topBarAction
import dev.mamkin.notemark.notes.presentation.notes.components.ProfileIcon
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        modifier = Modifier.clickable(onClick = {
                            onAction(CreateNoteAction.Close)
                        }),
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
                        modifier = Modifier.clickable {
                            onAction(CreateNoteAction.SaveNote)
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .fillMaxSize()
        ) {
            TransparentHintTextField(
                text = state.title,
                onValueChange = {
                    onAction(CreateNoteAction.OnTitleChange(it))
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                hintText = "Title",
                modifier = Modifier
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surface
            )
            TransparentHintTextField(
                text = state.content,
                onValueChange = {
                    onAction(CreateNoteAction.OnContentChange(it))
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                hintText = "Note content",
                modifier = Modifier,
                maxLines = Int.MAX_VALUE
            )
        }
    }
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