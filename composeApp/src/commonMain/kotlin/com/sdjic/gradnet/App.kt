package com.sdjic.gradnet

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.sdjic.gradnet.di.appModules
import com.sdjic.gradnet.presentation.screens.splash.SplashScreen
import com.sdjic.gradnet.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    AppTheme {
        KoinApplication(
            application = {
                modules(appModules)
            }
        ) {
            Navigator(SplashScreen())
        }
    }
}