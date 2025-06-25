package dev.mamkin.notemark.notes.presentation.notes

import dev.mamkin.notemark.core.domain.util.NetworkError

interface NotesEvent {
    data class NavigateToEdit(val id: String) : NotesEvent
}