package com.sdjic.gradnet

import GradNet_GraduateNetwork.composeApp.BuildConfig
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.sdjic.commons.helper.ConnectivityManager
import com.sdjic.data.network.Config
import com.sdjic.domain.AppCacheSetting
import com.sdjic.gradnet.di.appModules
import com.sdjic.gradnet.presentation.screens.onboarding.OnBoardingScreen
import com.sdjic.gradnet.presentation.screens.splash.SplashScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
    AppTheme {
        var authReady by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            ConnectivityManager.isConnected
            Config.setBaseUrl(BuildConfig.BASE_URL)
            GoogleAuthProvider.create(
                credentials =
                GoogleAuthCredentials("352124325984-ce3q3af8eqh1oqr54b0k6lm9d2ir6vkq.apps.googleusercontent.com")
            )
            authReady = true
        }

        KoinApplication(
            application = {
                modules(appModules)
            }
        ) {
            val keyStore = getKoin().get<AppCacheSetting>()
            if (keyStore.isLoggedIn) {
                Navigator(SplashScreen()) { SlideTransition(it) }
            } else {
                if (authReady) {
                    Navigator(OnBoardingScreen())
                }
            }
        }
    }
}