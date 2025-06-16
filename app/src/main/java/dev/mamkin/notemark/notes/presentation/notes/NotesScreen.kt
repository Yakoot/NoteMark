package dev.mamkin.notemark.notes.presentation.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesRoot(
    viewModel: NotesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NotesScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun NotesScreen(
    state: NotesState,
    onAction: (NotesAction) -> Unit,
) {

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