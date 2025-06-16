package dev.mamkin.notemark.notes.presentation.notes

data class NotesState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)