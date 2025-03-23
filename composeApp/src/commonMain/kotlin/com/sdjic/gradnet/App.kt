package com.sdjic.gradnet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.sdjic.gradnet.di.appModules
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import com.sdjic.gradnet.presentation.screens.aboutUs.AboutUsScreen
import com.sdjic.gradnet.presentation.screens.splash.SplashScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    AppTheme {
        var authReady by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            ConnectivityManager.isConnected
            GoogleAuthProvider.create(
                credentials =
                GoogleAuthCredentials("352124325984-ce3q3af8eqh1oqr54b0k6lm9d2ir6vkq.apps.googleusercontent.com")
            )
            authReady = true
        }

        DisposableEffect(Unit) {
            onDispose {
                /*  coroutineScope.launch {
                      updateStatusApi(false) Call your API here
                  }
                  */
            }
        }

        KoinApplication(
            application = { modules(appModules) }) {
            if (authReady) {
//                Navigator(SplashScreen()){ FadeTransition(it) }
                Navigator(AboutUsScreen()){ FadeTransition(it) }
            }
        }
    }
}