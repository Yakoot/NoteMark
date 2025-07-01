package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.core.database.notes.NoteEntity
import dev.mamkin.notemark.core.domain.util.DataError
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface LocalNotesDataSource {
    suspend fun upsertNote(note: Note): EmptyResult<DataError.Local>
    suspend fun deleteNote(id: String)
    fun getNotes(): Flow<List<Note>>
    suspend fun getNote(id: String): Note?

}
