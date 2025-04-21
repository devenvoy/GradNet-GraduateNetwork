package com.sdjic.gradnet.presentation.composables.filter

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import network.chaintech.kmp_date_time_picker.utils.capitalize
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoleSelectionItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    userRole: UserRole,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .noRippleEffect(onClick)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.size(50.sdp).clip(CircleShape).background(Color(0xFF4987FB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(12.sdp).fillMaxSize(),
                    painter = painterResource(userRole.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(6.sdp))
            SText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = userRole.name.capitalize(),
                fontWeight = if (isSelected) Bold else W500,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 12.ssp,
            )
        }
    }
}
