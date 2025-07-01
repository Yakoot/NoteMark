package dev.mamkin.notemark.notes.presentation.editNote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.notemark.core.domain.util.onSuccess
import dev.mamkin.notemark.notes.domain.NotesRepository
import dev.mamkin.notemark.notes.domain.models.Note
import dev.mamkin.notemark.notes.presentation.notes.NotesEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class EditNoteViewModel(
    private val id: String,
    private val notesRepository: NotesRepository,
    private val isNew: Boolean = true,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private var currentNote: Note? = null

    private val eventChannel = Channel<EditNoteEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(EditNoteState())
    val state = _state
        .onStart {
            println("State started")
            if (!hasLoadedInitialData) {
                loadNote()
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EditNoteState()
        )

    private suspend fun loadNote() {
        currentNote = notesRepository.getNote(id)
        currentNote?.let {
            _state.value = EditNoteState(
                title = it.title,
                content = it.content
            )
        }
    }

    fun onAction(action: EditNoteAction) {
        when (action) {
            is EditNoteAction.OnTitleChange -> onTitleChange(action.value)
            is EditNoteAction.OnContentChange -> onContentChange(action.value)
            is EditNoteAction.SaveNote -> {
                saveNote()
            }
            EditNoteAction.Close -> onClose()
        }
    }

    private fun onClose() {
        viewModelScope.launch {
            if (state.value.title != currentNote?.title || state.value.content != currentNote?.content) {
                eventChannel.send(EditNoteEvent.ShowDiscardDialog)
                return@launch
            }
            if (state.value.content.isEmpty() && isNew) {
                notesRepository.deleteNote(id)
            }
            eventChannel.send(EditNoteEvent.Close)
        }
    }

    private fun onTitleChange(value: String) {
        _state.update { it.copy(title = value) }
    }

    private fun onContentChange(value: String) {
        _state.update { it.copy(content = value) }
    }

    private fun saveNote() {
        viewModelScope.launch {
            val note = currentNote?.copy(
                title = state.value.title,
                content = state.value.content,
                lastEditedAt = ZonedDateTime.now()
            )
            note?.let {
                notesRepository.updateNote(it)
                    .onSuccess {
                        eventChannel.send(EditNoteEvent.Close)
                    }
            }
        }
    }

    fun clear() {
        _state.value = EditNoteState()
        currentNote = null
        hasLoadedInitialData = false
    }

}
