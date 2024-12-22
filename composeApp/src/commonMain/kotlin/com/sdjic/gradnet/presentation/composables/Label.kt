package com.sdjic.gradnet.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    size: TextUnit = 32.ssp
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = size,
        fontWeight = fontWeight
    )
}