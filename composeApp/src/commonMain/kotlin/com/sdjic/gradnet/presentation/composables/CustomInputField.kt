package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun CustomInputField(
    fieldTitle: String,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    minHeight: Dp = 42.sdp,
    singleLine: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isEnable: Boolean = true
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(
            text = fieldTitle,
            modifier = Modifier.padding(start = 2.sdp),
            textColor = Color.Gray,
        )
        TextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(8.sdp),
            trailingIcon = trailingIcon,
            enabled = isEnable,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                errorContainerColor = Color(0xFFF5F5F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .border(
                    width = 1.sdp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
                .background(Color.White),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = LocalTextStyle.current.copy(fontSize = 12.ssp),
            placeholder = placeholder,
        )
    }
}

@Composable
fun CustomInputPasswordField(
    fieldTitle: String,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    minHeight: Dp = 56.dp,
    singleLine: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    isPasswordField: Boolean = false,
    isEnable: Boolean = true
) {
    // State to manage password visibility
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(
            text = fieldTitle,
            modifier = Modifier.padding(start = 2.sdp),
            textColor = Color.Black,
        )
        TextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(8.sdp),
            trailingIcon = {
                if (isPasswordField) {
                    val visibilityIcon =
                        if (passwordVisible) FeatherIcons.Eye else FeatherIcons.EyeOff

                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = visibilityIcon,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Gray
                        )
                    }
                }
            },
            enabled = isEnable,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                errorContainerColor = Color(0xFFF5F5F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .border(
                    width = 1.sdp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
                .background(Color.White),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPasswordField && !passwordVisible) KeyboardType.Password else KeyboardType.Text
            ),
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = LocalTextStyle.current.copy(fontSize = 12.ssp),
            placeholder = placeholder,
        )
    }
}

