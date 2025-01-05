package com.sdjic.gradnet.data.local.preference

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import com.sdjic.gradnet.domain.AppCacheSetting

class AppCacheSettingImpl : AppCacheSetting {

    private val settings: Settings by lazy { Settings() }
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    override var accessToken: String
        get() = settings[SettingStorageKeys.ACCESS_TOKEN.key] ?: ""
        set(value) {
            settings[SettingStorageKeys.ACCESS_TOKEN.key] = value
        }

    override val isLoggedIn: Boolean
        get() = (settings[SettingStorageKeys.ACCESS_TOKEN.key] ?: "").isNotEmpty()

    override var userId: String
        get() = settings[SettingStorageKeys.USER_ID.key] ?: ""
        set(value) {
            settings[SettingStorageKeys.USER_ID.key] = value
        }

    override fun logout(callBack: () -> Unit) {
        settings.clear()
        callBack()
    }
}