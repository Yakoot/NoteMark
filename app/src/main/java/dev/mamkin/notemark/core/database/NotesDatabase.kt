package dev.mamkin.notemark.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mamkin.notemark.core.database.converters.UuidConverter
import dev.mamkin.notemark.core.database.notes.NoteEntity
import dev.mamkin.notemark.core.database.notes.NotesDao

@Database(
    entities = [NoteEntity::class],
    version = 1,
)
@TypeConverters(
    UuidConverter::class,
)
abstract class NotesDatabase: RoomDatabase() {
    abstract val notesDao: NotesDao
}