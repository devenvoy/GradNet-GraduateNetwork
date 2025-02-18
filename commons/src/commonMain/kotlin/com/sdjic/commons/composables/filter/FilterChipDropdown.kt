package com.sdjic.commons.composables.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.sdjic.commons.composables.filter.TopicDropdown

@Composable
fun FilterChipDropdown(
    modifier: Modifier = Modifier,
) {

    var selectedTopics by remember { mutableStateOf(setOf<String>()) }
    var savedTopics by remember {
        mutableStateOf(
            setOf(
                "Work",
                "Hobby",
                "Personal",
                "Office",
                "Workout"
            )
        )
    }
    var isAddingTopic by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column {

        TopicsList(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectedTopics = { selectedTopics = it },
            isAddingTopic = isAddingTopic,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            keyboardController = keyboardController,
            focusRequester = focusRequester,
        )

        if (searchQuery.isEmpty()) return

        TopicDropdown(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectedTopicsChange = { selectedTopics = it },
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            savedTopics = savedTopics,
            onSavedTopicsChange = { savedTopics = it },
        )
    }
}