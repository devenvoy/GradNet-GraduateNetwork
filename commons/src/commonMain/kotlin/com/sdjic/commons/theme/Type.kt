package com.sdjic.commons.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sdjic.shared.Resource as Res
import org.jetbrains.compose.resources.Font


@Composable
fun displayFontFamily() = FontFamily(
    Font(Res.font.interRegular, weight = FontWeight.W400)
)
