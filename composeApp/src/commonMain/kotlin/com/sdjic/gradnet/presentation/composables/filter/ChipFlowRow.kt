package com.sdjic.gradnet.presentation.composables.filter


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipFlowRow(
    modifier: Modifier = Modifier,
    selectedTopics: Set<String>,
    onSelectedTopics: (Set<String>) -> Unit,
    isAddingTopic: Boolean,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
) {

    LaunchedEffect(isAddingTopic) {
        if (isAddingTopic.not()) return@LaunchedEffect
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {

        selectedTopics.forEach { topic ->
            TopicChip(
                modifier = Modifier.padding(end = 4.dp),
                topic = topic,
                onCancel = { onSelectedTopics(selectedTopics - topic) }
            )
        }

        if (isAddingTopic.not()) {
            IconButton(
                onClick = { onAddingTopicChange(true) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(color = Color(0xFFEEF0FF), shape = CircleShape)
                    .size(32.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF40434F),
                    )
                }
            )
        } else {
            BasicTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp)
                    .focusRequester(focusRequester),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onAddingTopicChange(false)
                        keyboardController?.hide()
                    }
                )
            )
        }
    }
}