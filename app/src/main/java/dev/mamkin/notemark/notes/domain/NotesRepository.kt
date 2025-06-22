package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun observeNotes(): Flow<List<Note>>
    suspend fun createNote(title: String, content: String)
    suspend fun updateNote(id: String, title: String, content: String)
}