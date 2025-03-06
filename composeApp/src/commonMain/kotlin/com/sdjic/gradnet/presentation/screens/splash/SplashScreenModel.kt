package com.sdjic.gradnet.presentation.screens.splash

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import kotlinx.coroutines.launch

class SplashScreenModel(
    private val pref: AppCacheSetting,
    private val userRepo: UserRepository,
) : ScreenModel {

    var isLoading = mutableStateOf(false)
    var showNoInternetDialog = mutableStateOf(false)
    private var stopCurrentFlow = mutableStateOf(false)
    var navigateNext = mutableStateOf(false)


    private val isNetworkAvailable: Boolean
        get() = ConnectivityManager.isConnected

    fun checkFlow() {
        if (!isNetworkAvailable) {
            showNoInternetDialog.value = true
        } else {
            showNoInternetDialog.value = false
            startAuthFlow()
        }
    }

    private fun startAuthFlow() {
        isLoading.value = true

        screenModelScope.launch {
            val result = userRepo.checkUpdateToken(pref.accessToken)
            isLoading.value = false

            result.onSuccess { r ->
                r.value?.get("token")?.let { pref.accessToken = it }
            }.onError {
                stopCurrentFlow.value = true
            }

            navigateNext.value = true
        }
    }

    fun isUserLoggedIn(): Boolean = pref.isLoggedIn
    fun isUserVerified(): Boolean = pref.isVerified
}