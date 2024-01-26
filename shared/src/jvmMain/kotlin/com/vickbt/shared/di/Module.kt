package com.vickbt.shared.di

import com.vickbt.shared.utils.DatabaseDriverFactory
import com.vickbt.shared.utils.MultiplatformSettingsWrapper
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { MultiplatformSettingsWrapper().createSettings() }
}
