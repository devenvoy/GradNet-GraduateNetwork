package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScaleTransition
import com.sdjic.commons.helper.MyTab
import com.sdjic.commons.helper.MyTabOptions
import com.sdjic.event.EventScreen
import com.sdjic.shared.Resource as Res

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
                selectedIcon = Res.drawable.eventNote,
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