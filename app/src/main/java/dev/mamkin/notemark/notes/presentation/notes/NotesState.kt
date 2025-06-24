package dev.mamkin.notemark.notes.presentation.notes

import dev.mamkin.notemark.notes.presentation.notes.models.NoteUIModel

data class NotesState(
    val username: String = "",
    val notes: List<NoteUIModel> = emptyList()
)