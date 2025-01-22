package com.sdjic.gradnet.presentation.composables.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    size: TextUnit = 12.ssp,
    fontWeight: FontWeight? = FontWeight.SemiBold,
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = displayFontFamily(),
        style = TextStyle(
            color = textColor,
            fontWeight = fontWeight,
            fontSize = size,
        ),
    )
}