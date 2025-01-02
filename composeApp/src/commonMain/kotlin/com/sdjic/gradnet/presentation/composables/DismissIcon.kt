package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.cross
import org.jetbrains.compose.resources.painterResource

@Composable
fun DismissIcon(
    modifier: Modifier = Modifier
        .size(20.dp)
        .offset(x = 5.dp),
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            modifier = modifier
                .align(Alignment.TopEnd),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(Res.drawable.cross),
                tint = Color.Unspecified,
                contentDescription = "cross",
            )
        }
    }
}