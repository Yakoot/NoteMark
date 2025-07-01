package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.core.domain.util.DataError
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.notes.domain.models.Note

interface RemoteNotesDataSource {
    suspend fun createNote(note: Note): EmptyResult<DataError.Network>
    suspend fun updateNote(note: Note): EmptyResult<DataError.Network>
    suspend fun deleteNote(noteId: String): EmptyResult<DataError.Network>
    suspend fun getNotes(): Result<List<Note>, DataError.Network>
}
