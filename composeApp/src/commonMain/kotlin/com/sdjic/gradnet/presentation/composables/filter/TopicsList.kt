package com.sdjic.gradnet.presentation.composables.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.text.Title


@Composable
fun TopicsList(
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

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
                spotColor = Color(0xFF474F60).copy(alpha = 0.08f)
            ),
        color = Color(0xFFD9E2FF),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Title("Topics")

            ChipFlowRow(
                selectedTopics = selectedTopics,
                onSelectedTopics = onSelectedTopics,
                isAddingTopic = isAddingTopic,
                onAddingTopicChange = onAddingTopicChange,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                keyboardController = keyboardController,
                focusRequester = focusRequester,
            )
        }
    }
}