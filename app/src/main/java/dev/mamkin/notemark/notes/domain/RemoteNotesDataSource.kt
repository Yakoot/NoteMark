package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.NetworkError
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.notes.domain.models.Note

interface RemoteNotesDataSource {
    suspend fun createNote(note: Note): Result<Note, NetworkError>
    suspend fun updateNote(note: Note): Result<Note, NetworkError>
    suspend fun deleteNote(noteId: String): EmptyResult<NetworkError>
    suspend fun getNotes(): Result<List<Note>, NetworkError>
}