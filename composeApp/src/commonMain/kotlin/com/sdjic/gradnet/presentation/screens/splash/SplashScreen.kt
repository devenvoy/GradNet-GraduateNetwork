package com.sdjic.gradnet.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sdjic.gradnet.presentation.screens.home.HomeScreen
import com.sdjic.gradnet.presentation.screens.onboarding.OnBoardingScreen
import com.sdjic.gradnet.presentation.screens.verification.UserVerificationScreen
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.splash1
import gradnet_graduatenetwork.composeapp.generated.resources.splash2
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.DotLottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class SplashScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<SplashScreenModel>()

        val isLoading by viewModel.isLoading
        val navigateNext by viewModel.navigateNext
        val composition by rememberLottieComposition {
            LottieCompositionSpec.DotLottie(Res.readBytes("files/loading.lottie"))
        }

        LaunchedEffect(Unit) {
            viewModel.checkFlow()
        }

        LaunchedEffect(navigateNext) {
            if (navigateNext) {
                navigateToNextScreen(navigator, viewModel)
                viewModel.resetNavigation()
            }
        }

        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            SplashScreenContent()
            if (viewModel.showNoInternetDialog.value) {
                NoInternetDialog { viewModel.checkFlow() }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier.padding(bottom = 100.dp).fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        modifier = Modifier.size(100.sdp),
                        painter = rememberLottiePainter(
                            composition = composition,
                            iterations = Compottie.IterateForever
                        ),
                        contentDescription = "loader"
                    )
                }
            }
        }
    }

    @Composable
    fun SplashScreenContent() {

        val isDarkTheme = isSystemInDarkTheme()
        val appIconImage by remember(isDarkTheme){
            derivedStateOf {
                if(isDarkTheme) Res.drawable.splash2 else Res.drawable.splash1
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.size(200.dp),
                shape = CardDefaults.shape
            ) {
                Image(
                    painter = painterResource(appIconImage),
                    contentDescription = "user profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    @Composable
    private fun NoInternetDialog(onTryAgain: () -> Unit) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = onTryAgain) {
                    Text("Try Again")
                }
            },
            dismissButton = {
                TextButton(onClick = { }) { Text("Cancel") }
            },
            text = { Text("No internet connection. Please check your connection and try again.") }
        )
    }

    private fun navigateToNextScreen(navigator: Navigator, viewModel: SplashScreenModel) {
        if (viewModel.isUserLoggedIn()) {
            if (viewModel.isUserVerified()) {
                navigator.replace(HomeScreen())
            } else {
                navigator.replace(UserVerificationScreen())
            }
        } else {
            navigator.replace(OnBoardingScreen())
        }
    }
}