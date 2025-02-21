package com.sdjic.gradnet.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpScreen
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import com.sdjic.gradnet.presentation.screens.onboarding.OnBoardingScreen
import org.koin.compose.koinInject

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pref = koinInject<AppCacheSetting>()
        LaunchedEffect(Unit) {
            if (pref.isLoggedIn) {
                if (pref.isVerified) {
                    navigator.replace(HomeScreen())
                } else {
                    navigator.replace(SetUpScreen(false))
                }
            } else {
                navigator.replace(OnBoardingScreen())
            }
        }
        SplashScreenContent()
    }

    @Composable
    fun SplashScreenContent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}