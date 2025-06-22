package dev.mamkin.notemark.core.database.di

import androidx.room.Room
import dev.mamkin.notemark.core.database.NotesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import kotlin.jvm.java

val databaseModule = module {
    single<NotesDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            NotesDatabase::class.java,
            "notes.db",
        ).build()
    }
    single {
        get<NotesDatabase>().notesDao
    }
}