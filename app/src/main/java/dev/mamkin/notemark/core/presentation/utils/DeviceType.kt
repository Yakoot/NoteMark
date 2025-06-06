package dev.mamkin.notemark.core.presentation.utils

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
                widthClass == WindowWidthSizeClass.COMPACT &&
                        heightClass == WindowHeightSizeClass.MEDIUM -> MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.COMPACT &&
                        heightClass == WindowHeightSizeClass.EXPANDED -> MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.EXPANDED &&
                        heightClass == WindowHeightSizeClass.COMPACT -> MOBILE_LANDSCAPE
                widthClass == WindowWidthSizeClass.MEDIUM &&
                        heightClass == WindowHeightSizeClass.EXPANDED -> TABLET_PORTRAIT
                widthClass == WindowWidthSizeClass.EXPANDED &&
                        heightClass == WindowHeightSizeClass.MEDIUM -> TABLET_LANDSCAPE
                else -> DESKTOP
            }
        }
    }
}