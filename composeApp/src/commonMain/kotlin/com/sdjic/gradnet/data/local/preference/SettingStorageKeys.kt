package com.sdjic.gradnet.data.local.preference

enum class SettingStorageKeys {
    ACCESS_TOKEN,
    USER_ID,
    IS_LOGGED_IN;

    val key get() = this.name
}