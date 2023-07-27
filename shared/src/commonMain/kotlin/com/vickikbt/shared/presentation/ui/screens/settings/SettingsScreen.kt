package com.vickikbt.shared.presentation.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vickikbt.shared.domain.utils.Constants.KEY_IMAGE_QUALITY
import com.vickikbt.shared.domain.utils.Constants.KEY_THEME
import com.vickikbt.shared.presentation.ui.components.appbars.AppBar
import com.vickikbt.shared.presentation.ui.components.preferences.DialogPreferenceSelection
import com.vickikbt.shared.presentation.ui.components.preferences.PreferencesGroup
import com.vickikbt.shared.presentation.ui.components.preferences.TextPreference
import org.koin.compose.koinInject

private val themeLabels = listOf("Light", "Dark", "System Default")
private val imageQualityLabels = listOf("High Quality", "Low Quality")

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinInject()) {

    LaunchedEffect(key1 = viewModel) {
        viewModel.getThemePreference()
        viewModel.getImageQualityPreference()
    }

    val settingsUiState = viewModel.settingsUiState.collectAsState().value

    val showThemeDialog = remember { mutableStateOf(false) }
    val showImageQualityDialog = remember { mutableStateOf(false) }

    val themeLabel = themeLabels[settingsUiState.selectedTheme]
    val imageQualityLabel = imageQualityLabels[settingsUiState.selectedImageQuality]

    Scaffold(topBar = { AppBar("Settings") }) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                PreferencesGroup(title = "Personalisation") {
                    TextPreference(
                        icon = Icons.Rounded.Lightbulb,
                        title = "Change theme",
                        subTitle = themeLabel,
                        onClick = { showThemeDialog.value = !showThemeDialog.value }
                    )

                    if (showThemeDialog.value) ChangeTheme(
                        viewModel = viewModel,
                        showDialog = showThemeDialog,
                        currentValue = themeLabel
                    )

                    TextPreference(
                        icon = Icons.Rounded.Image,
                        title = "Image quality",
                        subTitle = imageQualityLabel,
                        onClick = { showImageQualityDialog.value = !showImageQualityDialog.value }
                    )

                    if (showImageQualityDialog.value) ChangeImageQuality(
                        viewModel = viewModel,
                        showDialog = showImageQualityDialog,
                        currentValue = imageQualityLabel
                    )
                }
            }
        }
    }
}

@Composable
private fun ChangeTheme(
    viewModel: SettingsViewModel,
    showDialog: MutableState<Boolean>,
    currentValue: String?
) {
    DialogPreferenceSelection(
        showDialog = showDialog.value,
        title = "Change theme",
        currentValue = currentValue ?: "Default",
        labels = themeLabels,
        onNegativeClick = { showDialog.value = false }
    ) { theme ->
        viewModel.savePreferenceSelection(key = KEY_THEME, selection = theme)
    }
}

@Composable
private fun ChangeImageQuality(
    viewModel: SettingsViewModel,
    showDialog: MutableState<Boolean>,
    currentValue: String?
) {
    DialogPreferenceSelection(
        showDialog = showDialog.value,
        title = "Image quality",
        currentValue = currentValue ?: "Default",
        labels = imageQualityLabels,
        onNegativeClick = { showDialog.value = false }
    ) { imageQuality ->
        viewModel.savePreferenceSelection(
            key = KEY_IMAGE_QUALITY,
            selection = imageQuality
        )
    }
}

private fun reportBug() {}

private fun openSourceCode() {}
