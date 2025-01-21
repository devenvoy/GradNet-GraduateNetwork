package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.composables.EmptyScreen
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions
import com.sdjic.gradnet.presentation.helper.UiStateHandler

// to see latest job req post and search job based on requirements
object JobsTab : MyTab {

    override val options: TabOptions
        @Composable get() = remember {
            TabOptions(
                index = 1u,
                title = "Jobs"
            )
        }

    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 1u,
                title = "Jobs",
                selectedIcon = Icons.Default.Work,
                unselectedIcon = Icons.Outlined.Work
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen(title = "Jobs Screen")
    }
}