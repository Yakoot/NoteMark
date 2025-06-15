package dev.mamkin.notemark.core.presentation.util

import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

enum class DeviceType {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    fun isTablet() = this == TABLET_PORTRAIT || this == TABLET_LANDSCAPE
    fun isPortrait() = this == MOBILE_PORTRAIT || this == TABLET_PORTRAIT

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceType {
            val widthClass = windowSizeClass.windowWidthSizeClass
            val heightClass = windowSizeClass.windowHeightSizeClass

            return when {
                widthClass == WindowWidthSizeClass.Companion.COMPACT &&
                        heightClass == WindowHeightSizeClass.Companion.MEDIUM -> MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.Companion.COMPACT &&
                        heightClass == WindowHeightSizeClass.Companion.EXPANDED -> MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.Companion.EXPANDED &&
                        heightClass == WindowHeightSizeClass.Companion.COMPACT -> MOBILE_LANDSCAPE
                widthClass == WindowWidthSizeClass.Companion.MEDIUM &&
                        heightClass == WindowHeightSizeClass.Companion.EXPANDED -> TABLET_PORTRAIT
                widthClass == WindowWidthSizeClass.Companion.EXPANDED &&
                        heightClass == WindowHeightSizeClass.Companion.MEDIUM -> TABLET_LANDSCAPE
                else -> DESKTOP
            }
        }
    }
}