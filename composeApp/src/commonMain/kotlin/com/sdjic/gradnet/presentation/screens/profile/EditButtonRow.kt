package com.sdjic.gradnet.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sdjic.commons.composables.text.SText
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun EditButtonRow(onEditClick: () -> Unit, onShareClick: () -> Unit) {
    Row(modifier = Modifier.padding(8.sdp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.sdp)) {
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
            modifier = Modifier.weight(1f),
            onClick = onShareClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .1f)
            )
        ) {
            SText("Share Profile")
        }
    }
}