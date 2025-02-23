package com.sdjic.gradnet.presentation.composables.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun FilterChipDropdown(
    modifier: Modifier = Modifier,
    savedTopics: Set<String>,
    selectedTopics: Set<String>,
    onSelectedTopicChange: (Set<String>) -> Unit,
    onSavedTopicsChange: (Set<String>) -> Unit
) {

    var isAddingTopic by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column {

        TopicsList(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectedTopics = onSelectedTopicChange,
            isAddingTopic = isAddingTopic,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            keyboardController = keyboardController,
            focusRequester = focusRequester,
        )

        if (searchQuery.isEmpty()) return

        TopicDropdown(
            modifier = Modifier,
            selectedTopics = selectedTopics,
            onSelectedTopicsChange = onSelectedTopicChange,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            savedTopics = savedTopics,
            onSavedTopicsChange = onSavedTopicsChange
        )
    }
}