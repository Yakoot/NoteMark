package dev.mamkin.notemark.notes.data

import android.database.sqlite.SQLiteFullException
import dev.mamkin.notemark.core.database.notes.NoteEntity
import dev.mamkin.notemark.core.database.notes.NotesDao
import dev.mamkin.notemark.core.domain.util.DataError
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.notes.data.mappers.toNote
import dev.mamkin.notemark.notes.data.mappers.toNoteDto
import dev.mamkin.notemark.notes.data.mappers.toNoteEntity
import dev.mamkin.notemark.notes.domain.LocalNotesDataSource
import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class)
class RoomLocalNotesDataSource(
    private val notesDao: NotesDao,
) : LocalNotesDataSource {
    override suspend fun upsertNote(note: Note): EmptyResult<DataError.Local> {
        return try {
            notesDao.upsertNote(note.toNoteEntity())
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(id: String) {
        val note = notesDao.getNoteById(id)
        note?.let { notesDao.deleteNote(it) }
    }

    override fun getNotes(): Flow<List<Note>> {
        return notesDao.observeNotes().map { it.map { it.toNote() } }
    }

    override suspend fun getNote(id: String): Note? {
        return notesDao.getNoteById(id)?.toNote()
    }

}
