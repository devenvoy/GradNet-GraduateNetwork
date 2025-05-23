package com.sdjic.gradnet.presentation.composables.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sdjic.gradnet.presentation.theme.displayFontFamily

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    size: TextUnit = 26.sp
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = size,
        fontWeight = fontWeight,
        fontFamily = displayFontFamily()
    )
}