package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.github
import gradnet_graduatenetwork.composeapp.generated.resources.linkedin
import gradnet_graduatenetwork.composeapp.generated.resources.mail_outline
import gradnet_graduatenetwork.composeapp.generated.resources.phone
import gradnet_graduatenetwork.composeapp.generated.resources.twitter_bird
import network.chaintech.kmp_date_time_picker.ui.timepicker.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoreInfoSection(phoneNumber: String, email: String, socialUrls: SocialUrls?) {
    SText(
        text = "Contact Info",
        fontSize = 14.ssp,
        fontWeight = FontWeight.W600,
        textColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 6.sdp, vertical = 12.sdp)
    )
    // Phone
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.phone),
                contentDescription = null,
                modifier = Modifier.padding(4.sdp).size(22.sdp)
            )
        },
        headlineContent = {
            Text(
                text = phoneNumber,
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null
            )
        },
        modifier = Modifier.noRippleEffect {}
    )
    // email
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.mail_outline),
                contentDescription = null,
                modifier = Modifier.padding(6.sdp).size(22.sdp)
            )
        },
        headlineContent = {
            Text(
                text = email,
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null
            )
        },
        modifier = Modifier.noRippleEffect {}
    )
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.github),
                modifier = Modifier.padding(6.sdp).size(22.sdp),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                text = "Github",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null
            )
        },
        modifier = Modifier.noRippleEffect {}
    )
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.linkedin),
                modifier = Modifier.padding(6.sdp).size(22.sdp),
                tint = Color(0xFF0A66C2),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                text = "LinkedIn",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null
            )
        },
        modifier = Modifier.noRippleEffect {}
    )
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.twitter_bird),
                modifier = Modifier.padding(6.sdp).size(22.sdp),
                tint = Color(0xFF0A66C2),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                text = "Twitter",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null
            )
        },
        modifier = Modifier.noRippleEffect {}
    )
}


@Composable
fun ContactInfoItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.invoke()
        Column(modifier = Modifier.weight(1f)) {
            headlineContent()
            supportingContent?.invoke()
        }
        trailingContent?.invoke()

    }
}