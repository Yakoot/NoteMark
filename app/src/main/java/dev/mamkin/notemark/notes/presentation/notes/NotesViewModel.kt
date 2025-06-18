package dev.mamkin.notemark.notes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.notemark.core.data.datastore.UserProfileDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val userProfileDataStore: UserProfileDataStore
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(NotesState())
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
            initialValue = NotesState()
        )

    init {
        viewModelScope.launch {
            userProfileDataStore.usernameFlow
                .collect { newUsername ->
                    _state.update { it.copy(username = newUsername) }
                }
        }
    }

    fun onAction(action: NotesAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}