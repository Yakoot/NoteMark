package dev.mamkin.notemark.notes.presentation.editNote

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
) : ViewModel() {

    private var hasLoadedInitialData = false

    private var currentNote: Note? = null

    private val eventChannel = Channel<EditNoteEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(EditNoteState())
    val state = _state
        .onStart {
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
        println("Loading note with id: $id")
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
            else -> TODO("Handle actions")
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

}
