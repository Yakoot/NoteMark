package dev.mamkin.notemark.notes.data

import dev.mamkin.notemark.core.domain.util.DataError
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.notes.domain.LocalNotesDataSource
import dev.mamkin.notemark.notes.domain.NotesRepository
import dev.mamkin.notemark.notes.domain.RemoteNotesDataSource
import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val remoteNotesDataSource: RemoteNotesDataSource,
    private val localNotesDataSource: LocalNotesDataSource,
    private val applicationScope: CoroutineScope
): NotesRepository {
    override suspend fun observeNotes(): Flow<List<Note>> {
        return localNotesDataSource.getNotes()
    }

    override suspend fun createNote(note: Note): EmptyResult<DataError> {
        val localResult = localNotesDataSource.upsertNote(note)

        if (localResult !is Result.Success) {
            return localResult
        }

        return applicationScope.async {
            val remoteResult = remoteNotesDataSource.createNote(note)
            if (remoteResult is Result.Error) {
                return@async Result.Success(Unit)
            }

            return@async remoteResult
        }.await()
    }

    override suspend fun updateNote(note: Note): EmptyResult<DataError> {
        val localResult = localNotesDataSource.upsertNote(note)

        if (localResult !is Result.Success) {
            return localResult
        }

        return applicationScope.async {
            val remoteResult = remoteNotesDataSource.updateNote(note)
            if (remoteResult is Result.Error) {
                return@async Result.Success(Unit)
            }

            return@async remoteResult
        }.await()
    }

    override suspend fun deleteNote(id: String) {
        localNotesDataSource.deleteNote(id)
        applicationScope.async {
            remoteNotesDataSource.deleteNote(id)
        }.await()
    }

    override suspend fun getNote(id: String): Note? {
        return localNotesDataSource.getNote(id)
    }
}
