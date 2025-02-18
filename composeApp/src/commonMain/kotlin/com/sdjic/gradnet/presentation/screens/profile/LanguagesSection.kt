package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sdjic.gradnet.presentation.composables.filter.InterestTag
import com.sdjic.gradnet.presentation.composables.text.SText
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LanguagesSection() {
    SText(
        text = "Languages",
        fontSize = 14.ssp,
        fontWeight = FontWeight.W600,
        textColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(start = 6.sdp, top = 12.sdp)
    )
    FlowRow(modifier = Modifier.padding(6.sdp)) {
        listOf( "Hindi",
            "English",
            "Gujarati",
            "Bengali",
            "Telugu",).forEach {
            InterestTag(it)
        }
    }
}
