package dev.mamkin.notemark.core.database.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity
data class NoteEntity(
    @PrimaryKey
    val id: Uuid = Uuid.NIL,
    val title: String,
    val content: String,
    val createdAt: Long,
    val lastEditedAt: Long,
)
