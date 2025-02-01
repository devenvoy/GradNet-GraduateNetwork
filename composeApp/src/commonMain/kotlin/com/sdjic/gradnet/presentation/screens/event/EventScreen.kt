package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sdjic.gradnet.presentation.composables.EmptyScreen


// for see about university and current affairs
class EventScreen : Screen {
    @Composable
    override fun Content() {
        EventScreenContent()
    }

    private @Composable
    fun EventScreenContent() {
        EmptyScreen(title = "Events Screen")
    }
}