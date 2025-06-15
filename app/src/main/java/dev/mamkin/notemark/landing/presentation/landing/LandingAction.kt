package dev.mamkin.notemark.landing.presentation.landing

sealed interface LandingAction {
    data object OnLoginClicked : LandingAction
    data object OnGetStartedClicked : LandingAction
}