package dev.mamkin.notemark.notes.presentation.createNote

data class CreateNoteState(
    val title: String = "Note title",
    val content: String = "Note content",
)