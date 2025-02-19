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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.LocalPlatformContext
import com.sdjic.commons.composables.images.RoundedCornerImage
import com.sdjic.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpScreen
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreen
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreen
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import com.sdjic.onboarding.AuthNavigatorAction
import com.sdjic.onboarding.OnBoardingScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pref = koinInject<AppCacheSetting>()
        LaunchedEffect(Unit) {
            delay(1000)
            if (pref.isLoggedIn) {
                if (pref.isVerified) {
                    navigator.replace(HomeScreen())
                } else {
                    navigator.replace(SetUpScreen(false))
                }
            } else {
                navigator.replace(OnBoardingScreen(AppNavigatorAction(navigator)))
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
            val context = LocalPlatformContext.current
            RoundedCornerImage(
                data = 0,
                context = context
            )
        }
    }


    class AppNavigatorAction(private val navigator: Navigator) : AuthNavigatorAction {
        override fun navigateToLogin() {
            navigator.replace(LoginScreen())
        }

        override fun navigateToSignUp() {
            navigator.replace(SignUpScreen())
        }
    }

}