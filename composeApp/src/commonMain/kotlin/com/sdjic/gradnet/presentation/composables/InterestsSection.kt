package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.sdjic.gradnet.presentation.composables.filter.InterestTag
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_contacts
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSection(
    icon: ImageVector = Icons.Outlined.Info,
    title: String = "Interests",
    data: List<String>?
) {
    if (data.isNullOrEmpty()) return
    SectionTitle(icon = icon, title = title)
    FlowRow(modifier = Modifier.padding(6.sdp)) {
        data.forEach { InterestTag(it) }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSection(
    icon: Painter = painterResource(Res.drawable.ic_contacts),
    title: String = "Interests",
    data: List<String>?
) {
    if (data.isNullOrEmpty()) return
    SectionTitle(icon = icon, title = title)
    FlowRow(modifier = Modifier.padding(6.sdp)) {
        data.forEach { InterestTag(it) }
    }
}
