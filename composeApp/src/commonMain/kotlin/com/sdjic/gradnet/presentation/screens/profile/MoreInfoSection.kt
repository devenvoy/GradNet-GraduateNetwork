package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_github_square_brands
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoreInfoSection() {
    Text(
        text = "More Info",
        style = typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    ListItem(
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.ic_github_square_brands),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                text = "Compose Cookbook github",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        supportingContent = { Text(text = "Tap to checkout the repo for the project") },
        modifier = Modifier
            .clickable(onClick = {/* launchSocialActivity(context, "repository")*/ })
    )
    ListItem(
        leadingContent = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) },
        headlineContent = {
            Text(
                text = "Contact Me",
                style = typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
        },
        supportingContent = { Text(text = "Tap to write me about any concern or info at $email") },
        modifier = Modifier
            .clickable(onClick = { /*launchSocialActivity(context, "repository")*/ })
    )
    ListItem(
        leadingContent = { Icon(imageVector = Icons.Rounded.Settings, contentDescription = null) },
        headlineContent = {
            Text(
                text = "Demo Settings",
                style = typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
        },
        supportingContent = { Text(text = "Not included yet. coming soon..") },
        modifier = Modifier.clickable(onClick = {})
    )
}
