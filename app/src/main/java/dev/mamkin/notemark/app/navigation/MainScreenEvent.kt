package dev.mamkin.notemark.app.navigation

import dev.mamkin.notemark.core.domain.util.NetworkError

interface MainScreenEvent {
    data object NavigateToLandingPage : MainScreenEvent
}