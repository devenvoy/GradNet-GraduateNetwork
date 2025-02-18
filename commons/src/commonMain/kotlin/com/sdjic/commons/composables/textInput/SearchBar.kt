package com.sdjic.commons.composables.textInput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdjic.commons.composables.text.SText

@Composable
fun SearchBar(
    text: String,
    hintText: String? = "Search",
    onChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onDismiss: () -> Unit,
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TextField(
        value = text,
        onValueChange = { value ->
            onChanged(value)
        },
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0xFFF8F8F8),
            unfocusedContainerColor = Color(0xFFF8F8F8),
        ),
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
            }
        ),
        singleLine = true,
        placeholder = {
            SText(
                text = hintText ?: "Search",
                textColor = Color.Gray,
                fontSize = 16.sp
            )
        },
        leadingIcon = {
            SearchIcon()
        },
        trailingIcon = {
            if (text.isNotEmpty())
                CancelIcon {
                    focusManager.clearFocus()
                    onChanged("")
                    onDismiss()
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
private fun CancelIcon(onCancel: () -> Unit) {
    Box(
        modifier = Modifier.clickable {
            onCancel()
        }
    ) {
        Icon(imageVector = Icons.Default.Cancel, tint = Color.Black, contentDescription = "cancel")
    }
}

@Composable
private fun SearchIcon() {
    Icon(imageVector = Icons.Default.Search, tint = Color.Black, contentDescription = "search")
}