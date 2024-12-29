package com.sdjic.gradnet

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import com.sdjic.gradnet.di.appModules
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import com.sdjic.gradnet.presentation.screens.onboarding.OnBoardingScreen
import com.sdjic.gradnet.presentation.screens.splash.SplashScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
    AppTheme {
        KoinApplication(
            application = {
                modules(appModules)
            }
        ) {
            val keyStore = getKoin().get<AppCacheSetting>()
            if (keyStore.isLoggedIn) {
                Navigator(SplashScreen())
            } else {
                ConnectivityManager.isConnected // just to call init
                Navigator(OnBoardingScreen())
            }
        }
    }
}