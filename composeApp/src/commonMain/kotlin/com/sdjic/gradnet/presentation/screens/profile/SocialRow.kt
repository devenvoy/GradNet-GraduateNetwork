package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_github_square_brands
import gradnet_graduatenetwork.composeapp.generated.resources.ic_linkedin_brands
import gradnet_graduatenetwork.composeapp.generated.resources.ic_twitter_square_brands
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialRow() {
    Card(
        elevation = CardDefaults.cardElevation(2.sdp),
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
//        val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            IconButton(onClick = { /*launchSocialActivity(context, "github")*/ }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_github_square_brands),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { /*launchSocialActivity(context, "twitter")*/ }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_twitter_square_brands),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { /*launchSocialActivity(context, "linkedin")*/ }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_linkedin_brands),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
