package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(14.sdp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.primaryContainer,

        ),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(
        defaultElevation = 2.sdp
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 14.sdp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = colors,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        content = content
    )
}

@Composable
fun SecondaryOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.sdp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.primaryContainer,
        containerColor = Color.White,
    ),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(defaultElevation = 1.dp),
    border: BorderStroke? = BorderStroke(1.sdp, MaterialTheme.colorScheme.primary),
    contentPadding: PaddingValues = PaddingValues(vertical = 14.sdp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = stringResource(Res.string.create_account),
            style = TextStyle(
                fontSize = 16.ssp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        shape = shape,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        elevation = elevation,
        content = content
    )
}