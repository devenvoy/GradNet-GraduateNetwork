package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sdjic.gradnet.di.platform_di.getContactsUtil
import com.sdjic.gradnet.presentation.composables.SectionTitle
import com.sdjic.gradnet.presentation.composables.button.ContactIconButton
import com.sdjic.gradnet.presentation.core.model.UserProfile
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
fun MoreInfoSection(
    isReadOnlyMode: Boolean,
    userProfile: UserProfile
) {

    SectionTitle(icon = painterResource(Res.drawable.ic_contacts), title = "Contacts")

    val contactsUtil by remember { mutableStateOf(getContactsUtil()) }
    val socialUrls = userProfile.socialUrls

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {

        // Phone
        if(isReadOnlyMode.not()){
            ContactIconButton(
                icon = painterResource(Res.drawable.phone),
                onClick = {
                    contactsUtil.dialPhoneNumber(userProfile.phoneNumber)
                }
            )
            ContactIconButton(
                icon = painterResource(Res.drawable.mail_outline),
                onClick = {
                    contactsUtil.sendEmail(userProfile.email, "", "")
                }
            )
        }else{
            if(userProfile.isPrivate){
                ContactIconButton(
                    icon = painterResource(Res.drawable.phone),
                    onClick = {
                        contactsUtil.dialPhoneNumber(userProfile.phoneNumber)
                    }
                )
                ContactIconButton(
                    icon = painterResource(Res.drawable.mail_outline),
                    onClick = {
                        contactsUtil.sendEmail(userProfile.email, "", "")
                    }
                )
            }
        }


        socialUrls?.github?.let {
            ContactIconButton(
                icon = painterResource(Res.drawable.github),
                onClick = {
                    contactsUtil.openLink(it)
                }
            )
        }

        socialUrls?.linkedIn?.let {
            ContactIconButton(
                icon = painterResource(Res.drawable.linkedin),
                onClick = {
                    contactsUtil.openLink(it)
                }
            )
        }
        socialUrls?.twitter?.let {
            ContactIconButton(
                icon = painterResource(Res.drawable.twitter_bird),
                onClick = {
                    contactsUtil.openLink(it)
                }
            )
        }

        socialUrls?.otherUrls?.forEach { url ->
            ContactIconButton(
                icon = painterResource(Res.drawable.ic_link),
                onClick = {
                    contactsUtil.openLink(url)
                }
            )
        }
    }
}
