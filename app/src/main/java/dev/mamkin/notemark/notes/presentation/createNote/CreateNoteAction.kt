package dev.mamkin.notemark.notes.presentation.createNote

sealed interface CreateNoteAction {
    object Close : CreateNoteAction
    object SaveNote : CreateNoteAction
    data class OnTitleChange(val value: String) : CreateNoteAction
    data class OnContentChange(val value: String) : CreateNoteAction
}