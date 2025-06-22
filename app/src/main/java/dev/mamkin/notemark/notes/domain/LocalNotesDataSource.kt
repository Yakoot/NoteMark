package dev.mamkin.notemark.notes.domain

import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface LocalNotesDataSource {
    suspend fun insertNote(title: String, content: String)
    fun getNotes(): Flow<List<Note>>

}