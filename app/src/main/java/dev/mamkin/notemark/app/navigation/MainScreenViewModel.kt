package dev.mamkin.notemark.app.navigation

import androidx.lifecycle.ViewModel
import dev.mamkin.notemark.core.data.datastore.TokenDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    tokenDataStore: TokenDataStore,
    coroutineScope: CoroutineScope
) : ViewModel() {
    private val eventChannel = Channel<MainScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        coroutineScope.launch {
            tokenDataStore.refreshTokenFlow.collectLatest {
                if (it.isNullOrBlank()) {
                    eventChannel.send(MainScreenEvent.NavigateToLandingPage)
                }
            }
        }
    }
}
