package com.sdjic.gradnet.presentation.composables.textInput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
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
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun CustomInputPasswordField(
    fieldTitle: String,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    minHeight: Dp = 56.dp,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
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
                        if (passwordVisible) Icons.Filled.LockOpen else Icons.Filled.Lock

                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.sdp),
                            imageVector = visibilityIcon,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            },
            enabled = isEnable,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPasswordField && !passwordVisible) KeyboardType.Password else KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 12.ssp,
                fontFamily = displayFontFamily()
            ),
            placeholder = placeholder,
        )
    }
}


@Composable
fun CustomInputPasswordField(
    fieldTitle: String,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    minHeight: Dp = 56.dp,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
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
                        if (passwordVisible) Icons.Filled.LockOpen else Icons.Filled.Lock

                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.sdp),
                            imageVector = visibilityIcon,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            },
            enabled = isEnable,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPasswordField && !passwordVisible) KeyboardType.Password else KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 12.ssp,
                fontFamily = displayFontFamily()
            ),
            placeholder = placeholder,
        )
    }
}