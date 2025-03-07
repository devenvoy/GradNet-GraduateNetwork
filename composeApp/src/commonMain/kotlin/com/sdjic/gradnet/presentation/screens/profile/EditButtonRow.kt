package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.text.SText
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_share
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.painterResource

@Composable
fun EditButtonRow(onEditClick: () -> Unit, onShareClick: () -> Unit) {

    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.sdp)
    ) {
        OutlinedButton(
            shape = RoundedCornerShape(4.sdp),
            modifier = Modifier.weight(1f),
            onClick = onEditClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .1f)
            )
        ) {
            SText("Edit Profile")
        }

        OutlinedButton(
            shape = RoundedCornerShape(4.sdp),
            onClick = onShareClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .1f)
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = "share"
            )
        }
    }
}