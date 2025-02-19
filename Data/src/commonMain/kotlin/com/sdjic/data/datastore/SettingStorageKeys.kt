package com.sdjic.data.datastore

enum class SettingStorageKeys {
    ACCESS_TOKEN,
    USER_ID,
    IS_VERIFIED,
    IS_LOGGED_IN;

    val key get() = this.name
}