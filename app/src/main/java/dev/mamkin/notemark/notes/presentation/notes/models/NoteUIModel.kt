package dev.mamkin.notemark.notes.presentation.notes.models

import dev.mamkin.notemark.notes.domain.models.Note
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class NoteUIModel(
    val id: String,
    val title: String,
    val content: String,
    val date: String,
)

fun Note.toUIModel(): NoteUIModel {
    val currentYear = LocalDate.now().year
    val noteYear = createdAt.year

    val formatter = if (noteYear == currentYear) {
        DateTimeFormatter.ofPattern("d MMM")
    } else {
        DateTimeFormatter.ofPattern("d MMM, yyyy")
    }

    return NoteUIModel(
        id = id,
        title = title,
        content = content,
        date = createdAt.format(formatter)
    )
}
