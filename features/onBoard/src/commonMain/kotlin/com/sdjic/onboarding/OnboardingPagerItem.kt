package com.sdjic.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.sdjic.commons.theme.displayFontFamily
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.DotLottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun OnboardingPagerItem(item: Onboard) {
    Column(modifier = Modifier.padding(start = 12.sdp, end = 12.sdp)) {

        val composition by rememberLottieComposition {
            LottieCompositionSpec.DotLottie(getOnboardingLottie(item.lottieFile))
        }

        Image(
            modifier = Modifier.size(300.sdp),
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            contentDescription = "loader"
        )
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
