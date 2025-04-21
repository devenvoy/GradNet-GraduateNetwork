package com.sdjic.gradnet.presentation.composables.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
fun ExpandableRichText(
    modifier: Modifier = Modifier,
    text: String,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold, color = Color(0xFF4987FB)),
    showLessText: String = " Show Less",
    showLessStyle: TextStyle = showMoreStyle
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }

    val richTextState = rememberRichTextState().apply { setMarkdown(text) }

    Column(modifier = modifier) {
        RichText(
            state = richTextState,
            modifier = Modifier.fillMaxWidth(),
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                isOverflowing = !isExpanded && result.hasVisualOverflow
            }
        )

        if (isOverflowing || isExpanded) {
            Text(
                text = if (isExpanded) showLessText else showMoreText,
                style = if (isExpanded) showLessStyle else showMoreStyle,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }
    }
}
