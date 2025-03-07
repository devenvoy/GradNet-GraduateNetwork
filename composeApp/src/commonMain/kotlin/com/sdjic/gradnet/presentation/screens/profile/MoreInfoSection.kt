package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sdjic.gradnet.presentation.composables.SectionTitle
import com.sdjic.gradnet.presentation.composables.button.ContactIconButton
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.github
import gradnet_graduatenetwork.composeapp.generated.resources.ic_contacts
import gradnet_graduatenetwork.composeapp.generated.resources.ic_link
import gradnet_graduatenetwork.composeapp.generated.resources.linkedin
import gradnet_graduatenetwork.composeapp.generated.resources.mail_outline
import gradnet_graduatenetwork.composeapp.generated.resources.phone
import gradnet_graduatenetwork.composeapp.generated.resources.twitter_bird
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoreInfoSection(phoneNumber: String, email: String, socialUrls: SocialUrls?) {

    SectionTitle(icon = painterResource(Res.drawable.ic_contacts), title = "Contacts")

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {

        // Phone
        ContactIconButton(
            icon = painterResource(Res.drawable.phone),
            onClick = {}
        )
        ContactIconButton(
            icon = painterResource(Res.drawable.mail_outline),
            onClick = {}
        )
        socialUrls?.github?.let {
            ContactIconButton(
                icon = painterResource(Res.drawable.github),
                onClick = {}
            )
        }
        socialUrls?.linkedIn?.let {
            ContactIconButton(
                icon = painterResource(Res.drawable.linkedin),
                onClick = {}
            )
        }
        socialUrls?.twitter?.let {
            ContactIconButton(
                icon = painterResource(Res.drawable.twitter_bird),
                onClick = {}
            )
        }

        socialUrls?.otherUrls?.forEach { url ->
            ContactIconButton(
                icon = painterResource(Res.drawable.ic_link),
                onClick = {}
            )
        }
    }
}
