package dev.mamkin.notemark.notes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotesResponse(
    val notes: List<NoteDto>,
    val total: Int,
)
