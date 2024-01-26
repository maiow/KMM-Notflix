package com.vickbt.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.vickbt.shared.ui.screens.settings.SettingsViewModel
import org.koin.compose.koinInject

/**
 * Append the image url with string to determine the image quality to be loaded
 */
@Composable
fun String.loadImage(): String {
    val settingsRepository: SettingsViewModel = koinInject()

    val quality =
        when (settingsRepository.settingsUiState.collectAsState().value.selectedImageQuality) {
            0 -> "original"
            else -> "w500"
        }

    return "https://image.tmdb.org/t/p/$quality/$this"
}
