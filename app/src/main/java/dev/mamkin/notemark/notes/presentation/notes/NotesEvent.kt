package dev.mamkin.notemark.notes.presentation.notes

interface NotesEvent {
    data class NavigateToEdit(val id: String) : NotesEvent
    data class NavigateToEditAfterCreation(val id: String) : NotesEvent
}
