package com.sdjic.commons.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.DotLottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp
import com.sdjic.shared.Resource as Res

@Composable
fun <T> UiStateHandler(
    uiState: UiState<T>,
    onErrorShowed: () -> Unit,
    content: @Composable (T) -> Unit // Handle Success state
) {

    val toaster = rememberToasterState()
    val composition by rememberLottieComposition {
        LottieCompositionSpec.DotLottie(Res.files.getLoadingLottie())
    }
    when (uiState) {
        is UiState.Error -> {
            LaunchedEffect(uiState.timestamp) {
                toaster.show(
                    message = uiState.message ?: "Unknown Error",
                    duration = ToasterDefaults.DurationLong,
                    type = ToastType.Error
                )
                onErrorShowed()
            }
            Toaster(
                modifier = Modifier.navigationBarsPadding(),
                state = toaster,
                richColors = true,
                darkTheme = isSystemInDarkTheme(),
                showCloseButton = true,
                alignment = Alignment.BottomCenter,
            )
        }

        is UiState.ValidationError -> {
            val errors = uiState.errors
            LaunchedEffect(uiState.timestamp) {
                errors.forEach { eMsg ->
                    toaster.show(
                        message = eMsg,
                        duration = ToasterDefaults.DurationLong,
                        type = ToastType.Error
                    )
                }
                onErrorShowed()
            }
            Toaster(
                modifier = Modifier.navigationBarsPadding(),
                state = toaster,
                richColors = true,
                darkTheme = isSystemInDarkTheme(),
                showCloseButton = true,
                alignment = Alignment.BottomCenter,
            )
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
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

        is UiState.Success -> {
            content(uiState.data) // Handle Success state separately
        }

        UiState.Idle -> {}
    }
}