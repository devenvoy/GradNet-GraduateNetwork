package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import com.sdjic.gradnet.presentation.composables.CircularProfileImage
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.core.DummyDpImage
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_alumni1
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopScrollingContent(scrollState: ScrollState) {
    val visibilityChangeFloat = scrollState.value > initialImageFloat - 20
    Row {
        AnimatedImage(scroll = scrollState.value.toFloat())
        Column(
            modifier = Modifier
                .padding(start = 8.sdp, top = 16.sdp)
                .alpha(animateFloatAsState(if (visibilityChangeFloat) 0f else 1f).value)
        ) {
            SText(
                text = name,
                fontSize = 12.ssp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.sdp)
            )
            SText(
                text = "Android developer",
                fontSize = 10.ssp,
                fontWeight = FontWeight(400)
            )
        }
    }
}

@Composable
fun AnimatedImage(scroll: Float) {
    val dynamicAnimationSizeValue = (initialImageFloat - scroll).coerceIn(26f, initialImageFloat)
    val platformContext = LocalPlatformContext.current
    CircularProfileImage(
        modifier = Modifier
            .padding(start = 12.sdp)
            .size(animateDpAsState(Dp(dynamicAnimationSizeValue)).value),
        context = platformContext,
        data = DummyDpImage,
        imageSize = 80.sdp
    )
}
