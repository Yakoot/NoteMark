package dev.mamkin.notemark.notes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.notemark.core.data.datastore.UserProfileDataStore
import dev.mamkin.notemark.core.domain.util.onSuccess
import dev.mamkin.notemark.notes.domain.NotesRepository
import dev.mamkin.notemark.notes.domain.models.Note
import dev.mamkin.notemark.notes.presentation.notes.models.toUIModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.Uuid

class NotesViewModel(
    private val notesRepository: NotesRepository,
    private val userProfileDataStore: UserProfileDataStore
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<NotesEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(NotesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeNotes()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NotesState()
        )

    init {
        subscribeOnUsernameFlow()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            notesRepository.observeNotes().collect { notes ->
                _state.update { it.copy(notes = notes.map { it.toUIModel() }) }
            }
        }
    }

    private fun subscribeOnUsernameFlow() {
        viewModelScope.launch {
            userProfileDataStore.usernameFlow
                .collect { newUsername ->
                    _state.update { it.copy(username = newUsername) }
                }
        }
    }

    fun onAction(action: NotesAction) {
        when (action) {
            is NotesAction.DeleteNote -> onDeleteNote(action.id)
            NotesAction.CreateNote -> onCreateNote()
            else -> TODO("Handle actions")
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun onCreateNote() {
        viewModelScope.launch {
            val uuid = Uuid.random()
            val time = ZonedDateTime.now()
            val note = Note(
                id = uuid.toString(),
                title = "Note title",
                content = "",
                createdAt = time,
                lastEditedAt = time
            )
            val result = notesRepository.createNote(note)
            result.onSuccess {
                eventChannel.send(NotesEvent.NavigateToEdit(note.id))
            }
        }
    }

    private fun onDeleteNote(id: String) {
        viewModelScope.launch {
            notesRepository.deleteNote(id)
        }
    }

}
