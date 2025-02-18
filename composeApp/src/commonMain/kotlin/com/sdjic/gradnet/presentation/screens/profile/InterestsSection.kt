package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sdjic.commons.composables.filter.InterestTag
import com.sdjic.commons.composables.text.SText
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSection() {
    SText(
        text = "Skills",
        fontSize = 14.ssp,
        fontWeight = FontWeight.W600,
        textColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(start = 6.sdp, top = 12.sdp)
    )
    FlowRow(modifier = Modifier.padding(6.sdp)) {
        listOf("Supply Chain Management",
            "Accounting",
            "Financial Analysis",
            "Budgeting",
            "Auditing",
            "Tax Preparation",
            "Human Resources Management",
            "Recruitment",).forEach {
            InterestTag(it)
        }
    }
}
