package com.sdjic.gradnet.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OnboardingPagerItem(item: Onboard) {
    Box(contentAlignment = Alignment.BottomCenter) {

        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/${item.lottieFile}").decodeToString()
            )
        }

        Image(
            modifier = Modifier.fillMaxSize().padding(bottom = 100.sdp),
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            contentDescription = "loader"
        )
        Column(modifier = Modifier.padding(bottom = 150.sdp, start = 12.sdp, end = 12.sdp)){
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontFamily = displayFontFamily(),
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 12.sdp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontFamily = displayFontFamily(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
