package com.sdjic.gradnet.presentation.screens.search

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.commons.composables.EmptyScreen

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        SearchScreenContent()
    }


    // for search any account / user /alumni / faculty / company to see details
    private @Composable
    fun SearchScreenContent() {
        EmptyScreen(title = "Search Screen")
    }
}