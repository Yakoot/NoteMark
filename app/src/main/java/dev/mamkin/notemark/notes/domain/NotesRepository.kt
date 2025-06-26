package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.core.domain.util.DataError
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun observeNotes(): Flow<List<Note>>
    suspend fun createNote(note: Note): EmptyResult<DataError>
    suspend fun updateNote(note: Note): EmptyResult<DataError>
    suspend fun deleteNote(id: String)
    suspend fun getNote(id: String): Note?
}
