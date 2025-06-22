package dev.mamkin.notemark.notes.presentation.notes

import dev.mamkin.notemark.notes.domain.models.Note

data class NotesState(
    val username: String = "",
    val notes: List<Note> = emptyList()
)