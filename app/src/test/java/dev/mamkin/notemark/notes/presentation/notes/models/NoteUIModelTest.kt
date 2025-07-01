package dev.mamkin.notemark.notes.presentation.notes.models

import dev.mamkin.notemark.notes.domain.models.Note
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZoneId
import java.time.ZonedDateTime

class NoteUIModelTest {
    
    @Test
    fun `toUIModel formats date correctly`() {
        // Create a Note with a specific date
        val noteId = "test-id"
        val noteTitle = "Test Title"
        val noteContent = "Test Content"
        val createdAt = ZonedDateTime.of(2023, 1, 15, 10, 30, 0, 0, ZoneId.systemDefault())
        val lastEditedAt = ZonedDateTime.of(2023, 1, 15, 10, 30, 0, 0, ZoneId.systemDefault())
        
        val note = Note(
            id = noteId,
            title = noteTitle,
            content = noteContent,
            createdAt = createdAt,
            lastEditedAt = lastEditedAt
        )
        
        // Convert to UI model
        val uiModel = note.toUIModel()
        
        // Verify the conversion
        assertEquals(noteId, uiModel.id)
        assertEquals(noteTitle, uiModel.title)
        assertEquals(noteContent, uiModel.content)
        assertEquals("Jan 15, 2023", uiModel.date)
    }
}
