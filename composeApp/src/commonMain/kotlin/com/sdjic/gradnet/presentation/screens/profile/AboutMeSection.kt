package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sdjic.gradnet.presentation.composables.text.SText
import com.sdjic.shared.resources.Res
import com.sdjic.shared.resources.about_me
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColumnScope.AboutMeSection(){
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
}