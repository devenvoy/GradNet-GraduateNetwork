package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import network.chaintech.sdpcomposemultiplatform.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(
    modifier: Modifier = Modifier,
    hintText: String,
    options: List<String>,
    onClick: ((Boolean) -> Unit),
    expanded: MutableState<Boolean>,
    onDismissReq: () -> Unit,
    selectedText: String,
    onValueSelected: (String) -> Unit,
) {

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = onClick
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                    .fillMaxWidth(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                placeholder = {
                    Text(hintText, color = Color.Gray)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray.copy(0.4f)
                ),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = { TrailingIcon(expanded = expanded.value) }
            )

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(0.86f).heightIn(max = 300.sdp),
                expanded = expanded.value,
                onDismissRequest = onDismissReq,
            ) {

                options.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            SText(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                text = item,
                                fontWeight = if (item == selectedText) FontWeight.Bold else null
                            )
                        },
                        onClick = {
                            onValueSelected(item)
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}