package com.sdjic.gradnet.presentation.composables.textInput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun CustomInputField(
    fieldTitle: String?= null,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    placeholder: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    suffix: @Composable() (() -> Unit)? = null,
    prefix: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
    isEnable: Boolean = true,
    keyboardOption: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    readOnly: Boolean = false
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        fieldTitle?.let {
            Title(
                text = fieldTitle,
                modifier = Modifier.padding(start = 2.sdp),
                textColor = MaterialTheme.colorScheme.onBackground,
            )
        }
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(8.sdp),
            trailingIcon = trailingIcon,
            enabled = isEnable,
            readOnly = readOnly,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f)
            ),
            suffix = suffix,
            prefix = prefix,
            singleLine = singleLine,
            keyboardOptions = keyboardOption.copy(
                imeAction = ImeAction.Next
            ),
            supportingText = supportingText,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 12.ssp,
                fontFamily = displayFontFamily()
            ),
            placeholder = placeholder,
        )
    }
}
