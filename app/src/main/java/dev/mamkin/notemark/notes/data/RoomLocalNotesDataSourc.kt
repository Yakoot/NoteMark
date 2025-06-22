package dev.mamkin.notemark.notes.data

import dev.mamkin.notemark.core.data.networking.constructUrl
import dev.mamkin.notemark.core.data.networking.safeCall
import dev.mamkin.notemark.core.database.notes.NoteEntity
import dev.mamkin.notemark.core.database.notes.NotesDao
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.NetworkError
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.core.domain.util.map
import dev.mamkin.notemark.notes.data.dto.NoteDto
import dev.mamkin.notemark.notes.data.dto.NotesResponse
import dev.mamkin.notemark.notes.data.mappers.toNote
import dev.mamkin.notemark.notes.data.mappers.toNoteDto
import dev.mamkin.notemark.notes.domain.LocalNotesDataSource
import dev.mamkin.notemark.notes.domain.RemoteNotesDataSource
import dev.mamkin.notemark.notes.domain.models.Note
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.Uuid
@OptIn(ExperimentalTime::class)
class RoomLocalNotesDataSource(
    private val notesDao: NotesDao,
) : LocalNotesDataSource {
    override suspend fun insertNote(title: String, content: String) {
        val uuid = Uuid.random()
        val time = Clock.System.now()
        val note = NoteEntity(
            id = uuid,
            title = title,
            content = content,
            createdAt = time.toEpochMilliseconds(),
            lastEditedAt = time.toEpochMilliseconds()
        )
        notesDao.insertNote(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return notesDao.observeNotes().map { it.map { it.toNote() } }
    }

}