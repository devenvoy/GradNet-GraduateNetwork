package com.sdjic.gradnet.presentation.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.UserDataSource

class HomeScreenViewModel(
    private val pref: AppCacheSetting,
    private val userDataSource: UserDataSource
) : ScreenModel {

    suspend fun appLogout() {
        pref.logout()
        userDataSource.clearDatabase()
    }
}
