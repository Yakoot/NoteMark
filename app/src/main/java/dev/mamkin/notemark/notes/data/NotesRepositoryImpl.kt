package dev.mamkin.notemark.notes.data

import dev.mamkin.notemark.notes.domain.LocalNotesDataSource
import dev.mamkin.notemark.notes.domain.NotesRepository
import dev.mamkin.notemark.notes.domain.RemoteNotesDataSource
import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val remoteNotesDataSource: RemoteNotesDataSource,
    private val localNotesDataSource: LocalNotesDataSource
): NotesRepository {
    override suspend fun observeNotes(): Flow<List<Note>> {
        return localNotesDataSource.getNotes()
    }

    override suspend fun createNote(title: String, content: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(
        id: String,
        title: String,
        content: String
    ) {
        TODO("Not yet implemented")
    }
}