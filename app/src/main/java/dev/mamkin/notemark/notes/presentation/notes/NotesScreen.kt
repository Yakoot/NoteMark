package dev.mamkin.notemark.notes.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.notemark.core.presentation.designsystem.theme.FabGradientEnd
import dev.mamkin.notemark.core.presentation.designsystem.theme.FabGradientStart
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import dev.mamkin.notemark.core.presentation.util.DeviceType
import dev.mamkin.notemark.notes.domain.models.Note
import dev.mamkin.notemark.notes.presentation.notes.components.NoteCard
import dev.mamkin.notemark.notes.presentation.notes.components.ProfileIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesRoot(
    viewModel: NotesViewModel = koinViewModel(),
    navigateToCreateNote: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NotesScreen(
        state = state,
        onAction = {
            when (it) {
                is NotesAction.CreateNote -> navigateToCreateNote()
                else -> viewModel.onAction(it)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    state: NotesState,
    onAction: (NotesAction) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowSizeClass(windowSizeClass)

    when (deviceType) {
        DeviceType.MOBILE_PORTRAIT -> {
            Scaffold(
                topBar = {
                    TopBar(
                        username = state.username
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
                floatingActionButton = {
                    CreateNoteButton { onAction(NotesAction.CreateNote) }
                }
            ) {
                if (state.notes.isEmpty()) {
                    EmptyNotesMessage(
                        modifier = Modifier.padding(it)
                    )
                } else {
                    NotesGrid(
                        contentPadding = it,
                        notes = state.notes,
                        onNoteClick = { onAction(NotesAction.OpenNote(it.id)) }
                    )
                }

            }
        }

        DeviceType.MOBILE_LANDSCAPE -> {
            Scaffold(
                topBar = {
                    TopBar(
                        username = state.username,
                        titlePadding = 44.dp
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
                floatingActionButton = {
                    CreateNoteButton { onAction(NotesAction.CreateNote) }
                }
            ) {
                if (state.notes.isEmpty()) {
                    EmptyNotesMessage(
                        modifier = Modifier.padding(it)
                    )
                } else {
                    NotesGrid(
                        contentPadding = it,
                        notes = state.notes,
                        onNoteClick = { onAction(NotesAction.OpenNote(it.id)) }
                    )
                }

            }
        }

        DeviceType.TABLET_PORTRAIT,
        DeviceType.TABLET_LANDSCAPE,
        DeviceType.DESKTOP,
            -> {
            Scaffold(
                topBar = {
                    TopBar(
                        username = state.username,
                        titlePadding = 8.dp
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
                floatingActionButton = {
                    CreateNoteButton { onAction(NotesAction.CreateNote) }
                }
            ) {
                if (state.notes.isEmpty()) {
                    EmptyNotesMessage(
                        modifier = Modifier.padding(it)
                    )
                } else {
                    NotesGrid(
                        contentPadding = it,
                        notes = state.notes,
                        onNoteClick = { onAction(NotesAction.OpenNote(it.id)) }
                    )
                }

            }
        }
    }
}

@Composable
private fun EmptyNotesMessage(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "You’ve got an empty board,\n" +
                "let’s place your first note on it!",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp, vertical = 80.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@Composable
private fun CreateNoteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier.size(64.dp),
        onClick = onClick,
        containerColor = Color.Transparent,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(FabGradientStart, FabGradientEnd)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    username: String,
    titlePadding: Dp = 0.dp,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = titlePadding),
                text = "NoteMark",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            ProfileIcon(
                modifier = Modifier.padding(end = 12.dp),
                username = username
            )
        },
    )
}

@Composable
private fun NotesGrid(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    columnCount: Int = 2,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalStaggeredGrid(
        contentPadding = contentPadding,
        columns = StaggeredGridCells.Fixed(columnCount),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(notes) {
            NoteCard(
                date = it.createdAt.toString(),
                title = it.title,
                content = it.content
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        NotesScreen(
            state = NotesState(),
            onAction = {}
        )
    }
}
