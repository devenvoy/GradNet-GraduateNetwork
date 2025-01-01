package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    placeholder: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    suffix: @Composable() (() -> Unit)? = null,
    prefix: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
    isEnable: Boolean = true,
    keyboardOption: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(
            text = fieldTitle,
            modifier = Modifier.padding(start = 2.sdp),
            textColor = MaterialTheme.colorScheme.onBackground,
        )
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(8.sdp),
            trailingIcon = trailingIcon,
            enabled = isEnable,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            ),
            suffix = suffix,
            prefix = prefix,
            singleLine = singleLine,
            keyboardOptions = keyboardOption.copy(
                imeAction = ImeAction.Next
            ),
            supportingText = supportingText,
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
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(
            text = fieldTitle,
            modifier = Modifier.padding(start = 2.sdp),
            textColor = MaterialTheme.colorScheme.onBackground,
        )
        OutlinedTextField(
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
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            },
            enabled = isEnable,
            colors = OutlinedTextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPasswordField && !passwordVisible) KeyboardType.Password else KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = LocalTextStyle.current.copy(fontSize = 12.ssp),
            placeholder = placeholder,
        )
    }
}

