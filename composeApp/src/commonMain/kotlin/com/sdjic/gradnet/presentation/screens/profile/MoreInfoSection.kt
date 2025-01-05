package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.sdjic.gradnet.presentation.composables.SText
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowRight
import compose.icons.feathericons.Mail
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_github_square_brands
import gradnet_graduatenetwork.composeapp.generated.resources.ic_linkedin_brands
import gradnet_graduatenetwork.composeapp.generated.resources.ic_twitter_square_brands
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoreInfoSection() {
    SText(
        text = "Contact Info",
        fontSize = 14.ssp,
        fontWeight = FontWeight.W600,
        textColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 6.sdp, vertical = 12.sdp)
    )
    // Phone
    ContactInfoItem(
        leadingContent = { Icon(imageVector = Icons.Rounded.Phone, contentDescription = null, modifier = Modifier.padding(4.sdp).size(22.sdp)) },
        headlineContent = {
            Text(
                text = "+91 9876543210",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null
            )
        },
        modifier = Modifier
            .clickable(onClick = {})
    )
    // email
    ContactInfoItem(
        leadingContent = { Icon(imageVector = FeatherIcons.Mail, contentDescription = null,modifier = Modifier.padding(6.sdp).size(22.sdp)) },
        headlineContent = {
            Text(
                text = "devanshamdavadwala@gmail.com",
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Icon(
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null
            )
        },
        modifier = Modifier
            .clickable(onClick = {})
    )
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.ic_github_square_brands),
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
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null
            )
        },
        modifier = Modifier
            .clickable(onClick = {})
    )
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.ic_linkedin_brands),
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
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null
            )
        },
        modifier = Modifier
            .clickable(onClick = {})
    )
    ContactInfoItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.ic_twitter_square_brands),
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
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null
            )
        },
        modifier = Modifier
            .clickable(onClick = {})
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