package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.SText
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.about_me
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomScrollingContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {

        EditButtonRow(onEditClick = {}, onShareClick = {})
//        SocialRow()
        SText(
            text = "About Me",
            fontSize = 14.ssp,
            fontWeight = FontWeight.W600,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 6.sdp, top = 12.sdp)
        )
        SText(
            text = stringResource(Res.string.about_me),
            modifier = Modifier
                .padding(6.sdp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .5f),
                    RoundedCornerShape(3.sdp)
                )
                .padding(6.sdp),
        )
        InterestsSection()
        LanguagesSection()
        MoreInfoSection()
    }
}
