package dev.mamkin.notemark.notes.presentation.createNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.notemark.notes.domain.RemoteNotesDataSource
import dev.mamkin.notemark.notes.domain.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CreateNoteViewModel(
    private val remoteNotesDataSource: RemoteNotesDataSource
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CreateNoteState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateNoteState()
        )

    fun onAction(action: CreateNoteAction) {
        when (action) {
            is CreateNoteAction.OnTitleChange -> onTitleChange(action.value)
            is CreateNoteAction.OnContentChange -> onContentChange(action.value)
            is CreateNoteAction.SaveNote -> {

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

    private suspend fun saveNote() {
//        val note = Note(
//            title = state.value.title,
//            content = state.value.content
//        )
//        remoteNotesDataSource.createNote(
//            Note
//        )
    }

}