package com.sdjic.gradnet

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.sdjic.gradnet.di.appModules
import com.sdjic.gradnet.presentation.screens.splash.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication(
            application = {
                modules(appModules)
            }
        ) {
            Navigator(SplashScreen())
        }
    }
}