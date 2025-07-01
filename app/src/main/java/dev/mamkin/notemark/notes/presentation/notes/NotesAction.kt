package dev.mamkin.notemark.notes.presentation.notes

sealed interface NotesAction {
    object CreateNote: NotesAction
    data class OpenNote(val id: String): NotesAction
    data class DeleteNote(val id: String): NotesAction
}
