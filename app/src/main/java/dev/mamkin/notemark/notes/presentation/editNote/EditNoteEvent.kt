package dev.mamkin.notemark.notes.presentation.editNote

interface EditNoteEvent {
    data object Close : EditNoteEvent
    data object ShowDiscardDialog : EditNoteEvent
}
