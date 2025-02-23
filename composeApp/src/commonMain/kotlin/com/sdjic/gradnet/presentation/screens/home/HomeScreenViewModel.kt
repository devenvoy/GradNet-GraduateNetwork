package com.sdjic.gradnet.presentation.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.domain.AppCacheSetting

class HomeScreenViewModel(
    private val pref: AppCacheSetting,
) : ScreenModel {


    fun appLogout() {
        pref.logout {

        }
    }
}
