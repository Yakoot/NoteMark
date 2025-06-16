package dev.mamkin.notemark.notes.data.mappers

import dev.mamkin.notemark.notes.data.dto.NoteDto
import dev.mamkin.notemark.notes.domain.models.Note
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun NoteDto.toNote(): Note  {
    val systemDefaultZone = ZoneId.systemDefault()

    val createdAtInstant = Instant.parse(this.createdAt)
    val lastEditedAtInstant = Instant.parse(this.lastEditedAt)

    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = ZonedDateTime.ofInstant(createdAtInstant, systemDefaultZone),
        lastEditedAt = ZonedDateTime.ofInstant(lastEditedAtInstant, systemDefaultZone)
    )
}

fun Note.toNoteDto(): NoteDto = NoteDto(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt.toIso8601String(),
    lastEditedAt = this.lastEditedAt.toIso8601String()
)

fun ZonedDateTime.toIso8601String(): String {
    val instant = this.toInstant()
    return instant.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
}