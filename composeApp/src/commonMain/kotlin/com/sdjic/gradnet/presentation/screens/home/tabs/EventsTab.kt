package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.outlined.Event
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.composables.EmptyScreen
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions


// for see about university and current affairs
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
                selectedIcon = Icons.Default.Event,
                unselectedIcon = Icons.Outlined.Event
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen(title = "Events Screen")
    }
}