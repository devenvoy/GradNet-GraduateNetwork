package com.sdjic.gradnet.presentation.screens.home.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sdjic.gradnet.presentation.composables.EmptyScreen
import com.sdjic.gradnet.presentation.helper.MyTab
import com.sdjic.gradnet.presentation.helper.MyTabOptions

// for search any account / user /alumni / faculty / company to see details
object SearchTab : MyTab {

    override val options: TabOptions
        @Composable get() = remember {
            TabOptions(2u, "Search")
        }
    override val tabOption: MyTabOptions
        @Composable get() = remember {
            MyTabOptions(
                index = 2u,
                title = "Search",
                selectedIcon = Icons.Default.Search,
                unselectedIcon = Icons.Outlined.Search
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen(title = "Search Screen")
    }
}