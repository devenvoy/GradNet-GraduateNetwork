package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScaleTransition
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.screens.event.EventScreen
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.event
import gradnet_graduatenetwork.composeapp.generated.resources.event_note

object EventsTab : MyTab {
    override val options: TabOptions
        @Composable get() = remember {
            TabOptions(
                index = 3u,
                title = "Events"
            )
        }
    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 3u,
                title = "Events",
                selectedIcon = Res.drawable.event_note,
                unselectedIcon = Res.drawable.event
            )
        }

    @Composable
    override fun Content() {
        Navigator(EventScreen()){
            ScaleTransition(it)
        }
    }
}