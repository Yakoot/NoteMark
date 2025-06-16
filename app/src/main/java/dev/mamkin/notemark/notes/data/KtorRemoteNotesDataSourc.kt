package dev.mamkin.notemark.notes.data

import dev.mamkin.notemark.core.data.networking.constructUrl
import dev.mamkin.notemark.core.data.networking.safeCall
import dev.mamkin.notemark.core.domain.util.EmptyResult
import dev.mamkin.notemark.core.domain.util.NetworkError
import dev.mamkin.notemark.core.domain.util.Result
import dev.mamkin.notemark.core.domain.util.map
import dev.mamkin.notemark.notes.data.dto.NoteDto
import dev.mamkin.notemark.notes.data.dto.NotesResponse
import dev.mamkin.notemark.notes.data.mappers.toNote
import dev.mamkin.notemark.notes.data.mappers.toNoteDto
import dev.mamkin.notemark.notes.domain.RemoteNotesDataSource
import dev.mamkin.notemark.notes.domain.models.Note
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class KtorRemoteNotesDataSource(
    private val httpClient: HttpClient,
) : RemoteNotesDataSource {
    override suspend fun createNote(note: Note): Result<Note, NetworkError> {
        return safeCall<NoteDto> {
            httpClient.post(
                urlString = constructUrl("/api/notes")
            ) {
                setBody(
                    note.toNoteDto()
                )
            }
        }.map { it.toNote() }
    }

    override suspend fun updateNote(note: Note): Result<Note, NetworkError> {
        return safeCall<NoteDto> {
            httpClient.put(
                urlString = constructUrl("/api/notes")
            ) {
                setBody(
                    note.toNoteDto()
                )
            }
        }.map { it.toNote() }
    }

    override suspend fun deleteNote(noteId: String): EmptyResult<NetworkError> {
        return safeCall {
            httpClient.delete(
                urlString = constructUrl("/api/notes/$noteId")
            )
        }
    }

    override suspend fun getNotes(): Result<List<Note>, NetworkError> {
        return safeCall<NotesResponse> {
            httpClient.get(
                urlString = constructUrl("/api/notes")
            )
        }.map { it.notes.map { it.toNote() } }
    }
}