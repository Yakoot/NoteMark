package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface LocalNotesDataSource {
    suspend fun insertNote(title: String, content: String): Note
    suspend fun deleteNote(id: String)
    fun getNotes(): Flow<List<Note>>
    suspend fun getNote(id: String): Note?

}