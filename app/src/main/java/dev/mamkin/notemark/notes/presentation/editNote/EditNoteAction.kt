package dev.mamkin.notemark.notes.presentation.editNote

sealed interface EditNoteAction {
    object Close : EditNoteAction
    object SaveNote : EditNoteAction
    data class OnTitleChange(val value: String) : EditNoteAction
    data class OnContentChange(val value: String) : EditNoteAction
}